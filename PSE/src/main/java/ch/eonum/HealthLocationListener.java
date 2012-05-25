package ch.eonum;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

public class HealthLocationListener implements LocationListener
{

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	@Override
	public void onLocationChanged(Location newLocation)
	{
		Logger.info(this.getClass().getName(), "Location changed: " + newLocation.toString());
		Location currentLocation = ((HealthActivity) HealthActivity.mainActivity).getLocation();
		if (isBetterLocation(newLocation, currentLocation))
		{
			Logger.info(this.getClass().getName(), "It is a better location");
			((HealthActivity) HealthActivity.mainActivity).setLocation(newLocation);
		}

		if (HealthActivity.userRequestedMyLocation)
		{
			// Draw current location to map and move to this point
			((HealthActivity) HealthActivity.mainActivity).drawMyLocation(16);
		}
		// If it seems the first location update, launch an initial search
		if (currentLocation == null)
		{
			((HealthActivity) HealthActivity.mainActivity).launchSearch(true);
		}
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		Toast.makeText(HealthActivity.mainActivity,
			HealthActivity.mainActivity.getString(R.string.provider_disabled, provider),
			Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		Toast.makeText(HealthActivity.mainActivity,
			HealthActivity.mainActivity.getString(R.string.provider_enabled, provider),
			Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// This is called when the location provider status alters
		switch (status)
		{
			case LocationProvider.OUT_OF_SERVICE:
			{
				Toast.makeText(HealthActivity.mainActivity,
					HealthActivity.mainActivity.getString(R.string.provider_status_out_of_service, provider),
					Toast.LENGTH_LONG).show();
				break;
			}
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
			{
				Toast.makeText(HealthActivity.mainActivity,
					HealthActivity.mainActivity.getString(R.string.provider_status_temp_unavailable, provider),
					Toast.LENGTH_LONG).show();
				break;
			}
			case LocationProvider.AVAILABLE:
			{
				Toast.makeText(HealthActivity.mainActivity,
					HealthActivity.mainActivity.getString(R.string.provider_status_available, provider),
					Toast.LENGTH_LONG).show();
				break;
			}
			default:
				Toast.makeText(HealthActivity.mainActivity,
					HealthActivity.mainActivity.getString(R.string.provider_status_unknown, provider),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Determines whether one Location reading is better than the current Location fix.
	 * 
	 * @param location
	 *            The new Location that you want to evaluate.
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new one.
	 * @return If the new location turns out to be more accurate, the method returns {@code true},
	 *         otherwise {@code false} is returned.
	 */
	protected boolean isBetterLocation(Location location, Location currentBestLocation)
	{
		if (currentBestLocation == null)
		{
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer)
		{
			return true;
			// If the new location is more than two minutes older, it must be worse
		}
		else if (isSignificantlyOlder)
		{
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate)
		{
			return true;
		}
		else if (isNewer && !isLessAccurate)
		{
			return true;
		}
		else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks whether two providers are the same.
	 */
	private boolean isSameProvider(String provider1, String provider2)
	{
		if (provider1 == null)
		{
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}
