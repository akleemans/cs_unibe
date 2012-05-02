/**
 * File: MatrikelNrComparator.java
 *
 * Klasse zum Vergleichen zweier Objekte (Records) vom Typ StudentIn
 * bezueglich der Matrikelnummer.
 *
 */

public class MatrikelNrComparator implements java.util.Comparator<StudentIn> {


  /**
   * Extrahiert die MatrikelNr aus dem Objekt, wenn es sich um
   * ein Objekt der Klasse StudentIn handelt.
   */
  private long extractMatrikelNr(StudentIn obj) {
	  return obj.getMatrikelNr();
  }

  /** Vergleicht Objekt a mit Objekt b und
   *  liefert -1 (wenn a<b), 0 (wenn a=b) oder +1 (wenn a>b) */
  public int compare(StudentIn a, StudentIn b) {
    return signum(extractMatrikelNr(a) - extractMatrikelNr(b));
  }

  /** Berechnet die Signum-Funktion einer ganzen Zahl */
  private static final int signum (long value) { 
    if (value > 0) return 1; 
    if (value < 0) return -1 ; 
    else return 0; 
  }

}
