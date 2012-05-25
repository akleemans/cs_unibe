package ch.eonum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

/**
 * This class represents a Logger with two main responsibilities:
 * 1. Print Logs to the screen (using android.util.Log)
 * 2. Save logs (i.e. for usability testing) to SD card of the device.
 * By using one global Mode "mode", we can assure that the tests will run correctly.
 * If the "TEST" or "PROD" mode is set, the Logger does nothing.
 */

public class Logger
{

	/** Defines the state of the application, as stated in {@link Mode}. */
	public static Mode mode = Mode.DEV;

	private static boolean writeToSD = false;
	private static String fileName = "logfile.csv";
	private static Timer timer;
	private static File file;
	private static File sdCard;
	private static FileOutputStream fos;

	public static void init()
	{
		if (mode == Mode.DEV)
		{
			if (writeToSD)
			{
				sdCard = Environment.getExternalStorageDirectory();
				file = new File(sdCard.getAbsolutePath() + "/Logs", fileName);
			}
			else
			{
				file = new File("/dev/null");
			}
			timer = new Timer();
			Logger.log("Logger initialized.");
		}
	}

	public static void log(String line)
	{
		if (mode == Mode.DEV)
		{
			Logger.info("Logger", ">> logged: " + line);

			byte[] data = new String(timer.timeElapsed() + ";" + line + "\n").getBytes();
			try
			{
				fos = new FileOutputStream(file, true);
				fos.write(data);
				fos.flush();
				fos.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void info(String tag, String msg)
	{
		if (mode == Mode.DEV)
		{
			Log.i(tag, msg);
		}
	}

	public static void warn(String tag, String msg)
	{
		if (mode == Mode.DEV)
		{
			Log.w(tag, msg);
		}
	}

	public static void error(String tag, String msg)
	{
		if (mode == Mode.DEV)
		{
			Log.e(tag, msg);
		}
	}
}
