package ch.eonum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;

/**
 * Resolves a medical category to a String and vice versa.
 */
public class CategoryResolver
{
	/**
	 * Stores the values for the different categories as keys and their translations as values.
	 * As a convention the descriptions are in male plural form.
	 * The values come from the server and the translations are stored in the strings.xml file.
	 */
	private static HashMap<String, String> categories = new HashMap<String, String>();
	private static CategoryResolver instance;
	private static boolean connectionError = true;

	/**
	 * Built as a Singleton to avoid retrieving the category keys all over again.
	 * 
	 * @return A new instance of CategoryResolver or an already existing one depending of the prior
	 *         availability of an Internet connection.
	 */
	public static synchronized CategoryResolver getInstance()
	{
		if (connectionError)
		{
			instance = new CategoryResolver();
		}
		return instance;
	}

	/**
	 * Retrieves a list of all categories the server is aware of
	 * and puts them into {@link #categories} along with the appropriate translation.
	 * If a translation is not listed in the strings.xml file, the identifier from the server is used instead
	 * and an error message is displayed.
	 */
	private CategoryResolver()
	{
		if (Logger.mode == Mode.TEST)
		{
			return;
		}
		HTTPRequest request = new HTTPRequest();
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
		Logger.info(this.getClass().getName(), "Size of results in CategoryResolver: " + resultString.length());

		if (resultString.length() == 0)
		{
			connectionError = true;
			return;
		}
		connectionError = false;

		// Parse results
		JSONParser parser = new JSONParser();

		ArrayList<String> categoryList = new ArrayList<String>();
		ArrayList<String> error = new ArrayList<String>();

		for (String categoryEntry : parser.deserializeCategories(resultString))
		{
			int id = HealthActivity.mainActivity.getResources().getIdentifier(categoryEntry, "string",
				this.getClass().getPackage().getName());

			String visibleDescription;
			try
			{
				visibleDescription = HealthActivity.mainActivity.getString(id);
			}
			catch (NotFoundException e)
			{
				visibleDescription = categoryEntry;
				error.add(visibleDescription);
			}
			CategoryResolver.categories.put(categoryEntry, visibleDescription);
			categoryList.add(visibleDescription);
		}

		if (!error.isEmpty())
		{
			Logger.error(this.getClass().getName(), "Some categories couldn't be resolved: " + error.toString());
		}
	}

	/**
	 * Used after instantiation time, this method returns all translation Strings of the available categories.
	 * 
	 * @return All category description Strings.
	 */
	public String[] getAllCategories()
	{
		String[] categoriesArray = new String[CategoryResolver.categories.size()];
		return CategoryResolver.categories.values().toArray(categoriesArray);
	}

	/**
	 * Get the translated string for a category key.
	 * 
	 * @param key
	 *            The key for a specific category as delivered by the server at instantiation time.
	 * @return Corresponding value how it will be displayed in the application.
	 */
	public String resolve(String key)
	{
		return CategoryResolver.categories.get(key);
	}

	/**
	 * Get the category key for a given category description.
	 * 
	 * @param value
	 *            Description as displayed in the application.
	 * @return Corresponding key as delivered by the server at instantiation time.
	 */
	public String getKeyByValue(String value)
	{
		for (Entry<String, String> entry : CategoryResolver.categories.entrySet())
		{
			if (value.equals(entry.getValue()))
			{
				return entry.getKey();
			}
		}
		return null;
	}
}
