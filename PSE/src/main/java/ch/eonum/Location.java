package ch.eonum;

/**
 * This class represents a geographical location and can be implemented.
 * Current Implementors are @{City} and @{MedicalLocation}.
 */

public interface Location
{
	public String getName();

	public Double[] getLocation();
}
