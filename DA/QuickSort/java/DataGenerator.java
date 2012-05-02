/**
 * File: DataGenerator.java
 *
 * Generiert Arrays mit zufaelligen Testdaten der Klasse StudentIn.
 */

import java.util.ArrayList;

public class DataGenerator {

  // Attribut:
  private ArrayList<StudentIn> myCurrentData;
  
  /** Generiert einen Array mit n Elementen vom Typ StudentIn. */
  public ArrayList<StudentIn> generateNewData(int n) {
    myCurrentData = new ArrayList<StudentIn>(n);
    for (int i=0; i<n; i++) {
      long matrikelNr = (long)(java.lang.Math.random()*20000000+80000000);
      String name = generateRandomString();
      String vorname = generateRandomString();
      String adresse = "ADRESSE";
      String plz = "3000";
      String ort = "BERN";
      myCurrentData.add(i, new StudentIn(matrikelNr, name, vorname,
                                       adresse, plz, ort));
    }
    return deepCopyArray(myCurrentData);
  }


  /** Liefert eine Kopie der zuletzt erzeugten Daten. */
  public ArrayList<StudentIn> getCopyOfData() {
    return deepCopyArray(myCurrentData);
  }

  // Erstellt eine tiefe Kopie des Arrays source:
  private ArrayList<StudentIn> deepCopyArray(ArrayList<StudentIn> source) {
    ArrayList<StudentIn> theCopy = new ArrayList<StudentIn>(source.size());
    for (int i=0; i<source.size(); i++)
      theCopy.add(i, source.get(i).clone());
    return theCopy;
  }

  // Erstellt einen zufaelligen String aus Buchstaben:
  private String generateRandomString() {
    int len = (int)(java.lang.Math.random()*7+3);
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<len; i++) {
      char c = (char)(java.lang.Math.random()*26+65);
      sb.append(c);
    }
    return sb.toString();
  }
}






