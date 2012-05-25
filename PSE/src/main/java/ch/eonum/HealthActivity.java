package ch.eonum;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

public class HealthActivity extends MapActivity implements HealthMapView.OnChangeListener
{
	/* Activity */
	public static Activity mainActivity;

	/* Other static variables */
	private static final String[] CITIES = CityResolver.getInstance().getAllCities();

	private static final int RESULTS_LIMIT = 20;
	private static String[] CATEGORIES;

	/* Location */
	/**
	 * Variables indicating the last known physical location of the user.
	 * <p>
	 * The value {@link #location} is provided by an external source to the
	 * {@link HealthLocationListener#onLocationChanged(Location)} method.<br>
	 * From there these variables are updated using the {@link #setLocation(Location)} method.
	 */
	private double latitude, longitude;

	/**
	 * Variable that is provided from an external source to
	 * the {@link HealthLocationListener#onLocationChanged(Location)} method.<br>
	 * From there the method {@link #setLocation(Location)} is used to update
	 * the variables {@link #latitude} and {@link #longitude} which are dependent from this value.
	 */
	private Location location = null;

	/** Variables involved in the process of getting location updates. */
	private LocationManager locMgr;
	private String locProvider;
	private LocationListener locLst = new HealthLocationListener();

	/**
	 * Involved in location updates to help decide if location updates should cause
	 * {@link #drawMyLocation(int)} to run.
	 */
	public static boolean userRequestedMyLocation = true;
	private CategoryResolver categoryResolver;

	/* Location Listener */
	/** Constants defining when a location update shall be delivered. */
	private final int LOCATION_UPDATE_AFTER_SECONDS = 5;
	private final float LOCATION_UPDATE_AFTER_DISTANCE = 100;

	/* Zoom */
	private int currentZoomLevel;

	/* Map */
	HealthMapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawableLocation, drawableDoctors, drawableHospitals;
	MapItemizedOverlay itemizedLocationOverlay, itemizedDoctorsOverlay, itemizedHospitalsOverlay;

	/* Menu */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.healthactivity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.menu_mAbout)
		{
			Intent intent = new Intent(HealthActivity.this, About.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* Location */
	public Location getLocation()
	{
		return this.location;
	}

	protected void setLocation(Location location)
	{
		this.location = location;
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	/* Map */
	protected MapView getMapView()
	{
		return this.mapView;
	}

	/**
	 * Main Activity:
	 * Called once when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Logger.info(this.getClass().getName(), "Main Activity started.");
		setContentView(R.layout.main);
		mainActivity = this;

		/** AutoCompleteTextView searchforWhere */
		AutoCompleteTextView searchforWhere = (AutoCompleteTextView) findViewById(R.id.searchforWhere);
		ArrayAdapter<String> adapterWhere =
			new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, CITIES);
		searchforWhere.setAdapter(adapterWhere);

		// Item from autocompletion selected
		searchforWhere.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String cityString = String.valueOf(((AutoCompleteTextView) findViewById(R.id.searchforWhere)).getText());
				City city = CityResolver.getInstance().getCity(cityString);
				GeoPoint cityPoint = new GeoPoint(
					(int) (city.getLocation()[0] * 1000000),
					(int) (city.getLocation()[1] * 1000000));
				MapController mc = HealthActivity.this.mapView.getController();
				mc.setZoom(16);
				mc.animateTo(cityPoint);
			}
		});

		// If enter is tapped, trigger search and hide keyboard
		searchforWhere.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
				{
					launchSearch(false); // Trigger search
					InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
						.getSystemService(INPUT_METHOD_SERVICE);
					// Hide keyboard
					inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		});

		/** AutoCompleteTextView searchforWhat */
		categoryResolver = CategoryResolver.getInstance();
		CATEGORIES = categoryResolver.getAllCategories();

		AutoCompleteTextView searchforWhat = (AutoCompleteTextView) findViewById(R.id.searchforWhat);
		ArrayAdapter<String> adapterWhat =
			new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, CATEGORIES);
		searchforWhat.setAdapter(adapterWhat);

		// If enter is tapped, trigger search and hide keyboard
		searchforWhat.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
				{
					launchSearch(false); // Trigger search
					InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
						.getSystemService(INPUT_METHOD_SERVICE);
					// Hide keyboard
					inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		});

		/** MapView */
		this.mapView = (HealthMapView) findViewById(R.id.mapview);
		this.mapView.setBuiltInZoomControls(true);
		this.mapView.setSatellite(true);
		// Add listener
		this.mapView.setOnChangeListener(this);

		this.mapOverlays = this.mapView.getOverlays();
		this.drawableLocation = this.getResources().getDrawable(R.drawable.pin_red);
		this.drawableDoctors = this.getResources().getDrawable(R.drawable.pin_green);
		this.drawableHospitals = this.getResources().getDrawable(R.drawable.pin_blue);
		this.itemizedLocationOverlay = new MapItemizedOverlay(this.drawableLocation, this);
		this.itemizedDoctorsOverlay = new MapItemizedOverlay(this.drawableDoctors, this);
		this.itemizedHospitalsOverlay = new MapItemizedOverlay(this.drawableHospitals, this);

		// Use the LocationManager class to obtain location updates
		this.locMgr = (LocationManager) this.getSystemService(LOCATION_SERVICE);

		/** ImageButton "getposition" */
		ImageButton getposition = (ImageButton) findViewById(R.id.getposition);
		getposition.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Logger.log("Location button pressed.");
				userRequestedMyLocation = true;
				drawMyLocation(16);
				// Clear the where field
				AutoCompleteTextView searchforWhere = (AutoCompleteTextView) findViewById(R.id.searchforWhere);
				// If possible, write current location address to where field
				searchforWhere.setText(getAddressFromCurrentLocation());

				launchSearch(true);
			}
		});

		/** Button "search" */
		Button search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				userRequestedMyLocation = false;

				Logger.log("Search button pressed.");

				if (currentZoomLevel < 4)
				{
					drawMyLocation(16);
				}

				MedicalLocation[] results = launchUserDefinedSearch();
				if (results != null)
				{
					if (results.length != 0)
					{
						MedicalLocation[] sortedResults = sortResultsByDistance(results);
						drawSearchResults(sortedResults);
					}
					// There is no need to display an error message here in case of an empty result list
					// as the search method already does this.
				}
				InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(
					INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});
		Logger.info(this.getClass().getName(), "Main Activity created.");
	}

	/**
	 * Every time, the activity is shown.
	 */
	@Override
	protected void onStart()
	{
		super.onStart();
		Logger.info(this.getClass().getName(), "Run onStart()");

		Logger.init();
		Logger.log("App started.");
	}

	/**
	 * Every time, the user returns to the activity.
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		Logger.log("App resumed.");

		if (this.locMgr == null)
		{
			this.locMgr = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
		}
		// Register the listener with the Location Manager to receive location updates
		// Moved from onCreate() here to avoid displaying the dialogue multiple times
		locationUpdateOrNetworkFail();
	}

	/**
	 * Activity loses focus.
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		Logger.log("App paused.");

		this.locMgr.removeUpdates(this.locLst);
		this.locMgr = null;
	}

	/**
	 * Activity is no longer visible.
	 */
	@Override
	protected void onStop()
	{
		super.onStop();
		Logger.log("App stopped.");

		itemizedLocationOverlay.clear();
		itemizedDoctorsOverlay.clear();
		itemizedHospitalsOverlay.clear();
		mapView.invalidateDrawable(drawableLocation);
		mapView.invalidateDrawable(drawableDoctors);
		mapView.invalidateDrawable(drawableHospitals);
		mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}

	/**
	 * This method is triggered every time the user moves onwards on the map.
	 * <p>
	 * It calculates the new location and visible map rectangle. After this it launches a new search from the
	 * targeted location.
	 */
	@Override
	public void onChange(MapView view, GeoPoint newCenter, GeoPoint oldCenter, int newZoom, int oldZoom)
	{
		this.currentZoomLevel = newZoom;

		launchSearch(false);

		// Do not display error messages if there were no results returned
		// because we do not want to disturb the user moving around the map.
	}

	/**
	 * Performs a complete search process: Ask server, sort results and draw results.
	 * 
	 * @param usePhysicalLocation
	 *            Set {@code true} if there should be searched for results near the MyLocation marker
	 *            or {@code false} if it should be the location where the user has just navigated.
	 */
	public void launchSearch(boolean usePhysicalLocation)
	{
		MedicalLocation[] results = launchSearchFromCurrentLocation(usePhysicalLocation);
		MedicalLocation[] sortedResults = sortResultsByDistance(results);
		drawSearchResults(sortedResults);
	}

	/**
	 * Depending of the input, this method launches a search with different arguments for the server.
	 * <p>
	 * A search is launched either with the coordinates where the MyLocation marker is placed or with the
	 * coordinates where the user has just navigated.
	 * <p>
	 * This method assumes that no user input is involved here and as such does no input checking and no error
	 * handling either.<br>
	 * If user input has to be taken into consideration, {@link #launchUserDefinedSearch()} should be used
	 * instead.
	 * 
	 * @param usePhysicalLocation
	 *            Set {@code true} if there should be searched for results near the MyLocation marker
	 *            or {@code false} if it should be the location where the user has just navigated.
	 * @return Filtered results.
	 */
	protected MedicalLocation[] launchSearchFromCurrentLocation(boolean usePhysicalLocation)
	{
		MedicalLocation[] answer;

		Editable searchWhat = ((AutoCompleteTextView) findViewById(R.id.searchforWhat)).getText();
		String what = searchWhat.toString();

		double lowerLeftLatitude = mapView.getMapCenter().getLatitudeE6() - mapView.getLatitudeSpan() / 2;
		double lowerLeftLongitude = mapView.getMapCenter().getLongitudeE6() - mapView.getLongitudeSpan() / 2;

		double upperRightLatitude = mapView.getMapCenter().getLatitudeE6() + mapView.getLatitudeSpan() / 2;
		double upperRightLongitude = mapView.getMapCenter().getLongitudeE6() + mapView.getLongitudeSpan() / 2;

		if (usePhysicalLocation
			|| (lowerLeftLatitude == 0 || lowerLeftLongitude == 0 || upperRightLatitude == 0 || upperRightLongitude == 0))
		{
			Logger.info(this.getClass().getName() + ": launchUserDefinedSearch",
				"Found no corners, querying for Long&Lat.");
			answer = sendDataToServer(this.latitude, this.longitude, what);
		}
		else
		{
			lowerLeftLatitude /= 1000000;
			lowerLeftLongitude /= 1000000;
			upperRightLatitude /= 1000000;
			upperRightLongitude /= 1000000;

			Logger.info(this.getClass().getName() + ": launchUserDefinedSearch", "Querying for map rectangle.");
			answer = sendDataToServer(lowerLeftLatitude, lowerLeftLongitude,
				upperRightLatitude, upperRightLongitude, what);
		}

		return answer;
	}

	/**
	 * Parses the two TextViews {@code searchforWhere} and {@code searchforWhat} and passes them to the server
	 * in order to get results.
	 * <p>
	 * Assumes that no other values should be taken into consideration.<br>
	 * If this is not the case, {@link #launchSearchFromCurrentLocation(boolean)} might be more suitable.
	 * <p>
	 * Handles user interaction in case of an error. If an error occurred, the method quits with a
	 * {@code null} value.
	 * 
	 * @return Filtered results or {@code null} indicating an error.
	 */
	protected MedicalLocation[] launchUserDefinedSearch()
	{
		double searchAtLatitude, searchAtLongitude;

		Editable searchWhere = ((AutoCompleteTextView) findViewById(R.id.searchforWhere)).getText();
		Editable searchWhat = ((AutoCompleteTextView) findViewById(R.id.searchforWhat)).getText();

		String where = searchWhere.toString();
		String what = searchWhat.toString();

		Logger.info(this.getClass().getName() + ": launchUserDefinedSearch", "Where: " + where + ", What: " + what);

		// Evaluate TextView searchforWhere
		if (where.length() != 0)
		{
			Geocoder geocoder = new Geocoder(this, getResources().getConfiguration().locale);
			List<Address> resultsList = null;
			String errorMessage = null;
			try
			{
				resultsList = geocoder.getFromLocationName(where + ", Schweiz", 5);
			}
			catch (IOException e)
			{
				errorMessage = e.getMessage();
				e.printStackTrace();
			}

			Logger.info(this.getClass().getName() + ": launchUserDefinedSearch", "resultsList: " + resultsList);
			// Inform user if the server returned no results
			if (resultsList == null || resultsList.isEmpty())
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setCancelable(true);
				builder.setTitle(getString(R.string.noresults));
				builder.setMessage(getString(R.string.serverresponse, errorMessage));
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setNeutralButton(android.R.string.ok, new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				return null;
			}

			// Ask user about ambiguous results
			if (resultsList.size() > 2)
			{
				final String[] ambiguousList = new String[resultsList.size()];
				for (int i = 0; i < resultsList.size(); i++)
				{
					ambiguousList[i] = resultsList.get(i).getAddressLine(0) + ", "
						+ resultsList.get(i).getAddressLine(1);
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setCancelable(false);
				builder.setTitle(getString(R.string.ambiguousresults, resultsList.size()));
				builder.setItems(ambiguousList, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int item)
					{
						AutoCompleteTextView searchForWhat = (AutoCompleteTextView) findViewById(R.id.searchforWhere);
						searchForWhat.setText(ambiguousList[item]);
						Button search = (Button) findViewById(R.id.search);
						search.performClick();
					}
				});
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setNeutralButton(android.R.string.cancel, new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(
					INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

				return null;
			}

			// Everything went fine, go to the location
			searchAtLatitude = resultsList.get(0).getLatitude();
			searchAtLongitude = resultsList.get(0).getLongitude();
			GeoPoint cityPoint = new GeoPoint(
				(int) (searchAtLatitude * 1000000),
				(int) (searchAtLongitude * 1000000));
			MapController mc = this.mapView.getController();
			mc.setZoom(16);
			mc.animateTo(cityPoint);
		}
		else
		{
			// TextView searchForWhere was empty, use current location
			searchAtLatitude = this.latitude;
			searchAtLongitude = this.longitude;
		}

		MedicalLocation[] answer = new MedicalLocation[] {};

		// Evaluate TextView searchForWhat
		if (what.length() != 0)
		{
			String whatValue = CategoryResolver.getInstance().getKeyByValue(what);
			// User entered an unknown category description
			if (whatValue == null)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setCancelable(false);
				builder.setTitle(getString(R.string.no_valid_category, what));
				builder.setMessage(R.string.choose_category_from_list);
				builder.setIcon(android.R.drawable.ic_dialog_info);
				builder.setNeutralButton(android.R.string.cancel, new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				return null;
			}

			answer = sendDataToServer(searchAtLatitude, searchAtLongitude, whatValue);
		}
		else
		{
			// User left category empty
			answer = sendDataToServer(searchAtLatitude, searchAtLongitude, "");
		}

		return answer;
	}

	/**
	 * Sorts the results by their distance.
	 * 
	 * @param results
	 *            Result list from the server.
	 * @return Result list sorted in ascending order by their distance.
	 */
	private MedicalLocation[] sortResultsByDistance(MedicalLocation[] results)
	{
		// Calculate distance of all result points from the current displayed position.
		for (MedicalLocation res : results)
		{
			double lat = (double) (mapView.getMapCenter().getLatitudeE6()) / 1000000;
			double lng = (double) (mapView.getMapCenter().getLongitudeE6()) / 1000000;
			res.setDistance(lat, lng);
		}

		// Sort the list, the higher the index, the longer the distance.
		Arrays.sort(results);

		return results;
	}

	/**
	 * Special case of sendDataToServer below, with only 1 coordinate.
	 */
	private MedicalLocation[] sendDataToServer(double latitude, double longitude, String category)
	{
		double d = 0.05;
		return sendDataToServer(latitude - d, longitude - d, latitude + d, longitude + d, category);
	}

	/**
	 * Generic method for sending a request to the server and return a result as a list.
	 */
	private MedicalLocation[] sendDataToServer(double lowerLeftLatitude, double lowerLeftLongitude,
		double upperRightLatitude, double upperRightLongitude, String category)
	{
		// Search for results around that point
		Logger.info(this.getClass().getName(), "Map rectangle to query: " + lowerLeftLatitude + "/"
			+ lowerLeftLongitude + "," + upperRightLatitude + "/" + upperRightLongitude);

		String typeKey = categoryResolver.getKeyByValue(category);

		HTTPRequest request = new HTTPRequest(lowerLeftLatitude, lowerLeftLongitude,
			upperRightLatitude, upperRightLongitude, typeKey, RESULTS_LIMIT);

		// Send query to server
		String resultString = "";
		AsyncTask<Void, Void, String> httpTask = request.execute();
		try
		{
			resultString = httpTask.get();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			// Restore the interrupted status
			Thread.currentThread().interrupt();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		Logger.info(this.getClass().getName(), "Size of results in queryServer: " + resultString.length());
		// Parse results
		JSONParser parser = new JSONParser();
		return parser.deserializeLocations(resultString);
	}

	/**
	 * Transforms the current location coordinates into an address.
	 * 
	 * @return Address of the current physical location or an empty String if the search failed.
	 * @see Geocoder
	 */
	public String getAddressFromCurrentLocation()
	{
		List<Address> myLocationAddressList;
		Address myLocationAddress;
		try
		{
			Geocoder geocoder = new Geocoder(this);
			myLocationAddressList = geocoder.getFromLocation(this.latitude, this.longitude, 1);
		}
		catch (IOException e)
		{
			return "";
		}

		if (myLocationAddressList == null || myLocationAddressList.isEmpty())
		{
			return "";
		}

		myLocationAddress = myLocationAddressList.get(0);
		String myAddressDescription = "";
		for (int i = 0; i <= myLocationAddress.getMaxAddressLineIndex(); i++)
		{
			myAddressDescription += myLocationAddress.getAddressLine(i)
				+ (i + 1 <= myLocationAddress.getMaxAddressLineIndex() ? "\n" : "");
		}
		return myAddressDescription;
	}

	/**
	 * Draws the current physical location.
	 * 
	 * @param zoomLevel
	 *            Google API zoom level, ranging from 1 (far away) to 16 (near).
	 */
	protected void drawMyLocation(int zoomLevel)
	{
		// Avoid NullPointerException in case location is not yet available
		if (this.location == null)
		{
			return;
		}

		this.latitude = this.location.getLatitude();
		this.longitude = this.location.getLongitude();
		Logger.info(this.getClass().getName() + ": drawMyLocation", this.latitude + " : " + this.longitude);

		// Remove other points
		this.itemizedLocationOverlay.clear();

		// Get address from current location
		String address = getAddressFromCurrentLocation();

		// Draw current location
		GeoPoint initGeoPoint = new GeoPoint((int) (this.latitude * 1000000), (int) (this.longitude * 1000000));
		OverlayItem overlayitem = new OverlayItem(initGeoPoint, getString(R.string.myposition), address);
		this.itemizedLocationOverlay.addOverlay(overlayitem);
		this.mapOverlays.add(this.itemizedLocationOverlay);

		// Go there
		MapController mc = getMapView().getController();
		mc.setZoom(zoomLevel);
		mc.animateTo(initGeoPoint);
	}

	/**
	 * Draws received MedicalLocations as GeoPoints onto the map.
	 * 
	 * @param results
	 *            MedicalLocations to draw.
	 */
	protected void drawSearchResults(MedicalLocation[] results)
	{
		// Remove other points
		this.itemizedDoctorsOverlay.clear();
		this.itemizedHospitalsOverlay.clear();

		// Draw results to map
		Logger.info(this.getClass().getName() + ": drawSearchResults", "Draw " + results.length + " results to map");
		Logger.info("GeoPoint", "Start drawing");

		for (MedicalLocation point : results)
		{
			Logger.info(String.format("GeoPoint is at %f : %f", point.getLocation()[0], point.getLocation()[1]),
				String.format("Draw GeoPoint \"%s (%s)\"", point.getName(), point.getCategories()));
			GeoPoint matchingResult = new GeoPoint(
				(int) (point.getLocation()[0] * 1000000),
				(int) (point.getLocation()[1] * 1000000)
				);
			
			String email = point.getEmail();
			String tel = point.getTelephone();
			
			if (email.length() == 0)
			{
				email = getString(R.string.no_email);
			}
			
			if (tel.length() == 0)
			{
				tel = getString(R.string.no_tel);
			}

			OverlayItem matchingOverlayitem = new OverlayItem(matchingResult, point.getName(), point.getCategories()
				+ "\n" + point.getAddress() + "\n" + email + "\n" + tel);
			if (CategoryResolver.getInstance().getKeyByValue(point.getCategories()) != getString(R.string.spitaeler))
			{
				this.itemizedDoctorsOverlay.addOverlay(matchingOverlayitem);
			}
			else
			{
				this.itemizedHospitalsOverlay.addOverlay(matchingOverlayitem);
			}
		}
		// Putting this lines outside the loop improved the performance drastically
		this.mapOverlays.add(this.itemizedLocationOverlay);
		this.mapOverlays.add(this.itemizedDoctorsOverlay);
		this.mapOverlays.add(this.itemizedHospitalsOverlay);

		Logger.info("GeoPoint", "Finished drawing");
	}

	/** This criteria will settle for less accuracy, high power, and cost. */
	private static Criteria createCoarseCriteria()
	{
		Criteria c = new Criteria();
		c.setAccuracy(Criteria.ACCURACY_COARSE);
		c.setAltitudeRequired(false);
		c.setBearingRequired(false);
		c.setSpeedRequired(false);
		c.setCostAllowed(true);
		c.setPowerRequirement(Criteria.POWER_HIGH);
		return c;
	}

	/** This criteria needs high accuracy, high power, and cost. */
	private static Criteria createFineCriteria()
	{
		Criteria c = new Criteria();
		c.setAccuracy(Criteria.ACCURACY_FINE);
		c.setAltitudeRequired(false);
		c.setBearingRequired(false);
		c.setSpeedRequired(false);
		c.setCostAllowed(true);
		c.setPowerRequirement(Criteria.POWER_HIGH);
		return c;
	}

	/**
	 * Perform network check and alert user if nothing works.
	 * <p>
	 * Make use of {@link #isLocationSensingAvailable()} to test for available location update providers. If
	 * one was found, everything is fine. Updates are requested and the method returns.<br>
	 * If this is not the case, the user is informed and assisted to fix the problem by displaying the network
	 * and location configuration activity.
	 */
	private void locationUpdateOrNetworkFail()
	{
		if (isLocationSensingAvailable())
		{
			this.locMgr.requestLocationUpdates(this.locProvider, LOCATION_UPDATE_AFTER_SECONDS * 1000,
				LOCATION_UPDATE_AFTER_DISTANCE, this.locLst);
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(getString(R.string.fail_no_provider));
		builder.setMessage(getString(R.string.ask_user_to_enable_location_sources));
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton(R.string.settings,
			new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					Intent locationSourceIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(locationSourceIntent);
				}
			});
		builder.setNegativeButton(android.R.string.cancel,
			new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					dialog.cancel();
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Location provider search
	 * <p>
	 * Searches for available location providers and chooses one based on {@link createCoarseCriteria} and
	 * {@link createFineCriteria}.
	 * 
	 * @return {@code True} if at least one provider was found, {@code False} if no reliable provider for
	 *         location updates is available.
	 */
	private boolean isLocationSensingAvailable()
	{
		String mBestProvider = null;

		String mBestFineProvider = this.locMgr.getBestProvider(createFineCriteria(), true);
		String mBestCoarseProvider = this.locMgr.getBestProvider(createCoarseCriteria(), true);
		// Prefer coarse provider
		mBestProvider = (mBestCoarseProvider == null ? mBestFineProvider : mBestCoarseProvider);
		if (mBestProvider != null)
		{
			this.locProvider = mBestProvider;
			return true;
		}
		// Even fine provider is not available
		return false;
	}
}
