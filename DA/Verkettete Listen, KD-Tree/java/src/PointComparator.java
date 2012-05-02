/**
 * File: PointComparator.java
 * Compares two
 *
 */
import java.awt.Point;

public class PointComparator implements java.util.Comparator<Point> {

	/**
	 * Dimension to compare: 0:x, 1:y
	 */
	int dimension;
	
	public PointComparator(int d){
		this.dimension = d;
	}
	
  /** Vergleicht Objekt a mit Objekt b und
   *  liefert -1 (wenn a<b), 0 (wenn a=b) oder +1 (wenn a>b) */
  public int compare(Point a, Point b) {
	  if(dimension == 0){
		  return signum(a.x - b.x);
	  }else{
		  return signum(a.y - b.y);
	  }
  }
  

  /** Berechnet die Signum-Funktion einer ganzen Zahl */
  private static final int signum (long value) { 
    if (value > 0) return 1; 
    if (value < 0) return -1 ; 
    else return 0; 
  }

}
