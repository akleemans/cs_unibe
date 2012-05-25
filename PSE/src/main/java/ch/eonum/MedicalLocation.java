package ch.eonum;

/**
 * Represents a single address (data set) returned by the server.
 * Can be a medical practice, hospital, doctor office etc.
 * Will provide public methods for name, categories and location.
 * Type depends on {@link CategoryResolver}, which matches the returned categories to names.
 */
public class MedicalLocation implements Location, Comparable<MedicalLocation>
{
	CategoryResolver resolver = CategoryResolver.getInstance();
	private String name;
	private String address;
	private String email;
	private String tel;
	private String[] categories;
	private double latitude;
	private double longitude;
	Double[] location = new Double[2];
	private double distance;

	public MedicalLocation(String name, String address, String email, String tel, String[] types, double latitude, double longitude)
	{
		this.name = name;
		this.address = address;
		this.email = email;
		this.tel = tel;
		this.latitude = latitude;
		this.location[0] = latitude;
		this.longitude = longitude;
		this.location[1] = longitude;
		this.categories = new String[types.length];

		for (int i = 0; i < types.length; i++)
		{
			this.categories[i] = resolver.resolve(types[i]);
			if (this.categories[i] == null)
			{
				Logger.warn("Category is Null", "Name: " + this.name + ", Index " + i + ": " + types[i]);
			}
		}
		this.distance = 0;
	}

	/* GETTERS & SETTERS */
	@Override
	public String getName()
	{
		return this.name;
	}

	public String getAddress()
	{
		return this.address;
	}

	public String getEmail()
	{
		return this.email;
	}
	
	public String getTelephone()
	{
		return this.tel;
	}

	/**
	 * If there is more than one category entry the categories are each listed on a new line.
	 * 
	 * @return All categories on separate lines.
	 */
	public String getCategories()
	{
		String categoryList = "";
		for (int i = 0; i < this.categories.length; i++)
		{
			categoryList += this.categories[i] + (i + 1 < this.categories.length ? ", " : "");
		}
		return categoryList;
	}

	@Override
	public Double[] getLocation()
	{
		return this.location;
	}

	public double getDistance()
	{
		return this.distance;
	}

	/**
	 * Calculate distance from current map center.
	 * Used to prefer results with a shorter distance from the current physical location.
	 * 
	 * @param latCenter
	 *            Latitude of map center.
	 * @param lngCenter
	 *            Longitude of map center.
	 * @return The approximate distance of the location from the current center of the map.
	 */
	public double setDistance(double latCenter, double lngCenter)
	{
		double deltaLat = Math.abs(latCenter - this.latitude);
		double deltaLng = Math.abs(lngCenter - this.longitude);

		this.distance = Math.sqrt(Math.pow(deltaLat, 2) + Math.pow(deltaLng, 2));
		return this.distance;
	}

	@Override
	public int compareTo(MedicalLocation loc)
	{
		return (int) Math.signum(this.distance - loc.getDistance());
	}
}
