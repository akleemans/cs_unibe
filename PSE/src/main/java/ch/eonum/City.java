package ch.eonum;

/**
 * Represents a geographical city, containing a name and the location data.
 */
public class City implements Location
{
	private String name;
	Double[] location = new Double[2];

	public City(String name, double latitude, double longitude)
	{
		this.name = name;
		this.location[0] = latitude;
		this.location[1] = longitude;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Double[] getLocation()
	{
		return location;
	}
}
