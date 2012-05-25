package ch.eonum;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.util.ByteArrayBuffer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * Fetches the raw data from the server.
 * This is done by reading the answer from the defined server
 * to a ByteArrayBuffer, which turns out to be quite swift.
 */

public class HTTPRequest extends AsyncTask<Void, Void, String>
{
	URL url;
	String resultString;
	private boolean internetAvailable = true;

	/**
	 * Prepares a regular search with certain delimiters.
	 * 
	 * @param lat1
	 *            Latitude of the lower left corner of the visible map.
	 * @param long1
	 *            Longitude of the lower left corner of the visible map.
	 * @param lat2
	 *            Latitude of the upper right corner of the visible map.
	 * @param long2
	 *            Longitude of the upper right corner of the visible map.
	 * @param category
	 *            Display only results which match the given category. Valid Strings are keys from
	 *            {@link CategoryResolver} as sent by the server at instantiation time.
	 * @param count
	 *            Limit the number of results that should be delivered from the server.
	 * @see CategoryResolver#getKeyByValue(String)
	 */
	public HTTPRequest(double lat1, double long1, double lat2, double long2, String category, int count)
	{
		this.resultString = "";
		internetAvailable = isOnline();

		String urlCategory;
		if (category != null)
		{
			urlCategory = "&category=" + category;
		}
		else
		{
			urlCategory = "";
		}

		String urlCount;
		if (count >= 0)
		{
			urlCount = "&count=" + count;
		}
		else
		{
			urlCount = "";
		}

		try
		{
			url = new URL("http://77.95.120.72:8080/finder?lat1=" + lat1 + "&long1=" + long1 + "&lat2=" + lat2
				+ "&long2=" + long2 + urlCategory + urlCount);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This constructor provides {@link CategoryResolver#CategoryResolver} with all available categories in
	 * the database.
	 */
	public HTTPRequest()
	{
		this.resultString = "";
		internetAvailable = isOnline();

		try
		{
			url = new URL("http://77.95.120.72:8080/categories");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isOnline()
	{
		ConnectivityManager cMgr =
			(ConnectivityManager) HealthActivity.mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
		return (netInfo != null && netInfo.isConnected());
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Void... params)
	{
		Timer t = new Timer();
		Logger.info(this.getClass().getName(), "Start reading answer from server at " + t.timeElapsed() + "ms");
		Logger.info(this.getClass().getName(), "   url: " + url);

		final int APPROX_MAX_PAGE_SIZE = 500;
		try
		{
			InputStream inputStream = url.openStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(APPROX_MAX_PAGE_SIZE);

			byte[] buf = new byte[APPROX_MAX_PAGE_SIZE];
			int read;
			do
			{
				read = bufferedInputStream.read(buf, 0, buf.length);
				if (read > 0)
				{
					byteArrayBuffer.append(buf, 0, read);
				}
			}
			while (read >= 0);
			this.resultString = new String(byteArrayBuffer.toByteArray());
		}
		catch (IOException e)
		{
			this.internetAvailable = false;
			e.printStackTrace();
		}

		Logger.info(this.getClass().getName(), "Size of HTTP answer: " + resultString.length()
			+ " at " + t.timeElapsed() + "ms");
		return resultString;
	}

	@Override
	protected void onPostExecute(String resString)
	{
		if (!this.internetAvailable)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(HealthActivity.mainActivity);
			builder.setCancelable(false);
			builder.setTitle(HealthActivity.mainActivity.getString(R.string.neterror));
			builder.setMessage(HealthActivity.mainActivity.getString(R.string.no_network_check_settings));
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setPositiveButton(R.string.settings,
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						Intent settingsIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
						HealthActivity.mainActivity.startActivity(settingsIntent);
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
		Logger.info(this.getClass().getName(), "Size of results from server in onPostExecute: " + resString.length());
		super.onPostExecute(resString);
	}
}
