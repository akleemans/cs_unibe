/**
 * File: MiniTest.java
 *
 * Einfaches Testprogramm zur �berpr�fung des QuickSorts.
 */

import java.util.ArrayList;

public class MiniTestApp {

  public static void main(String[] args) {

    //*** Test-Records bereitstellen:
    ArrayList<StudentIn> array = new ArrayList<StudentIn>(10);
    array.add(0, new StudentIn(92987654,"Habegger","Pascal","Gerberngasse 12","3011","Bern"));
    array.add(1, new StudentIn(92123456,"Wenger","Thomas","Hirschweid 511","3113","Rubigen"));
    array.add(2, new StudentIn(99876532,"Müller","Anton","Fliederweg 2","3000","Bern"));
    array.add(3, new StudentIn(98222634,"Stucki","Daniel","Amselweg 2","3600","Thun"));
    array.add(4, new StudentIn(90588921,"Müller","Kurt","Waldweg 87","3110","Münsingen"));
    array.add(5, new StudentIn(89345675,"Moser","Käthy","Hauptstrasse 20","2122","Kehrsatz"));
    array.add(6, new StudentIn(98345632,"Schneider","Anna","Mühleplatz 3","3000","Bern"));
    array.add(7, new StudentIn(94419832,"Briod","Jean","Wasserwerkgasse 22","3000","Bern"));
    array.add(8, new StudentIn(99323462,"Fischer","Hans","Rosenweg 2","3110","Münsingen"));
    array.add(9, new StudentIn(96419642,"Gans","Gustav","Erpelweg 13","1313","Entenhausen"));

    //*** Array sortieren:
    
    // Comparator bestimmt hinsichtlich was sortiert werden soll
    
    // Hier wird der vorgegebene MatrikelNrComparator verwendet:
    //java.util.Comparator<StudentIn> comp = new MatrikelNrComparator();
    
    // Testen Sie Ihren neuen NameVornameComparator indem Sie diesen Code
    // verwenden:
    java.util.Comparator<StudentIn> comp = new NameVornameComparator();
    
    // Sortieren mit QuickSort
	QuickSort.quickSort(array, 0 , array.size()-1, comp);

    //*** Array ausgeben:
    System.out.println("MatrikelNr Name Vorname");
    for (int i=0; i<array.size(); i++) {
      System.out.println(array.get(i).getMatrikelNr()+" "+array.get(i).getName()
		         +" "+array.get(i).getVorname());
    }

  }
}
