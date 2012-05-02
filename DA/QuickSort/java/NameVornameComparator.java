/**
 * File: NameVornameComparator.java
 *
 * Klasse zum Vergleichen zweier Objekte (Records) vom Typ StudentIn
 * bezueglich des Namens und des Vornamens.
 *
 */

public class NameVornameComparator implements java.util.Comparator<StudentIn> {

  /**
   * Extrahiert den vollen Namen aus dem Objekt, wenn es sich um
   * ein Objekt der Klasse StudentIn handelt.
   */
  private String extractFullName(StudentIn obj) {
	  return obj.getName()+" "+obj.getVorname();
  }

  /** Vergleicht Objekt a mit Objekt b und
   *  liefert -1 (wenn a<b), 0 (wenn a=b) oder +1 (wenn a>b) */
  public int compare(StudentIn a, StudentIn b) {
	  return signum(extractFullName(a),  extractFullName(b));
  }

  /** Vergleicht die beiden Zeichenketten miteinander. */
  private static final int signum (String a, String b) { 
    if (a.compareTo(b)>0) return 1; 
    else if (a.compareTo(b)<0) return -1 ; 
    else return 0; 
  }

}
