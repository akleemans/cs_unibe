/**
 * File: StudentIn.java
 *
 * Klasse zu einem Objekt, das die Daten einer StudentIn enthaelt.
 * Objekte dieser Klasse koennen als unsere zu sortierenden Records
 * betrachtet werden.
 */

public class StudentIn implements Cloneable {

	  // Die Attribute des Objekts:
	  private long     myMatrikelNr;
	  private String   myName;
	  private String   myVorname;
	  private String   myAdresse;
	  private String   myPlz;
	  private String   myOrt;

  
	  /** Konstruiert ein neues Objekt StudentIn. */
	  public StudentIn(long matrikelNr, String name, String vorname,
	                   String adresse, String plz, String ort) {
		    myMatrikelNr = matrikelNr;
		    myName = name;
		    myVorname = vorname;
		    myAdresse = adresse;
		    myPlz = plz;
		    myOrt = ort;
	  }

	  /** Erzeugt eine echte Kopie eines Objekts der Klasse StudentIn. */
	  public StudentIn clone() {
		    return new StudentIn(myMatrikelNr, new String(myName),
		                         new String(myVorname), new String(myAdresse),
		                         new String(myPlz), new String(myOrt));
	  }


	  // lesende Zugriffsmethoden:
	  public long getMatrikelNr() { return myMatrikelNr; }
	  public String getName() { return myName; }
	  public String getVorname() { return myVorname; }
	  public String getAdresse() { return myAdresse; }
	  public String getPlz() { return myPlz; }
	  public String getOrt() { return myOrt; }
	
	  // schreibende Zugriffsmethoden:
	  public void setName(String name) { myName = name; }
	  public void setVorname(String vorname) { myVorname = vorname; }
	  public void setAdresse(String adresse) { myAdresse = adresse; }
	  public void setPlz(String plz) { myPlz = plz; }
	  public void setOrt(String ort) { myOrt = ort; }
}
