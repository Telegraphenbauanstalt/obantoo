/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright 2006 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigef�gte lpgl.txt
 */
package de.jost_net.OBanToo.Dtaus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;

/**
 * Datentr�geraustauschdateien (DTAUS) erstellen
 * <p>
 * 
 * Beispiel f�r die Erstellung einer DTAUS-Datei:<br>
 * <br>
 * Zun�chst ist eine OutputStream (z. B. FileOutputStream) zu instanziieren:<br>
 * <code>
 * FileOutputStream fos = new FileOutputStream("dtaus"); <br><br>
 * </code> Anschlie�end wird ein DtausDateiWriter instanziiert, dem der
 * Outputstream im Konstruktor �bergeben wird:<br>
 * <code>
 * DtausDateiWriter dtausDateiWriter = new DtausDateiWriter(fos);<br>
 * </code> <br>
 * Jetzt wird der ASatz gef�llt und geschrieben:<br>
 * <code>
 * dtausDateiWriter.setAGutschriftLastschrift("GK");<br>
 * dtausDateiWriter.setABLZBank(40050060);<br>
 * dtausDateiWriter.setAKundenname("Donald Duck GmbH und Co");<br>
 * dtausDateiWriter.setAKonto(123456);<br>
 * dtausDateiWriter.writeASatz();<br>
 * </code> <br>
 * Ab hier werden die eigentlichen Zahlungss�tze erstellt:<br>
 * <code>
 * dtausDateiWriter.setCBLZEndbeguenstigt(10020030);<br>
 * dtausDateiWriter.setCKonto(444444);<br>
 * dtausDateiWriter.setCTextschluessel(CSatz.TS_UEBERWEISUNGSGUTSCHRIFT);<br>
 * dtausDateiWriter.setCInterneKundennummer(1); <br>
 * dtausDateiWriter.setCBetragInEuro(1000);<br>
 * dtausDateiWriter.setCName("Donald Duck");<br>
 * dtausDateiWriter.addCVerwendungszweck("bekannt");<br>
 * dtausDateiWriter.writeCSatz();<br>
 * <br>
 * dtausDateiWriter.setCBLZEndbeguenstigt(80077711);<br>
 * dtausDateiWriter.setCKonto(666);<br>
 * dtausDateiWriter.setCTextschluessel(CSatz.TS_UEBERWEISUNGSGUTSCHRIFT);<br>
 * dtausDateiWriter.setCInterneKundennummer(2);<br>
 * dtausDateiWriter.setCBetragInEuro(1000);<br>
 * dtausDateiWriter.setCName("Micky Maus");<br>
 * dtausDateiWriter.addCVerwendungszweck("la li lu");<br>
 * dtausDateiWriter.addCVerwendungszweck("nur der Mann im Mond ");<br>
 * dtausDateiWriter.addCVerwendungszweck("schaut zu ");<br>
 * dtausDateiWriter.addCVerwendungszweck("wenn die kleinen Kinder");<br>
 * dtausDateiWriter.addCVerwendungszweck("schlafen");<br>
 * dtausDateiWriter.writeCSatz();<br>
 * </code> <br>
 * Die Methode writeESatz schlie�t den OutputStream implizit<br>
 * <code>
 * dtausDateiWriter.writeESatz();<br>
 * System.out.println("DTAUS-Datei erstellt!");<br>
 *</code>
 */
public class DtausDateiWriter
{

  private static final long serialVersionUID = 8631473446209849972L;

  private DataOutputStream dos;

  private ASatz asatz;

  private CSatz csatz;

  private ESatz esatz;

  public DtausDateiWriter(OutputStream os) throws DtausException
  {
    dos = new DataOutputStream(os);
    asatz = new ASatz();
    csatz = new CSatz();
    esatz = new ESatz();
  }

  public void setAGutschriftLastschrift(String aGutschriftLastschrift)
      throws DtausException
  {
    asatz.setGutschriftLastschrift(aGutschriftLastschrift);
  }

  /**
   * Feld A4 - Bankleitzahl der Bank, bei der die Diskette eingereicht wird
   * G�ltige Werte: Siehe Verzeichnis der Bankleitzahlen Standardwert: 0
   */
  public void setABLZBank(long aBLZBank)
  {
    asatz.setBlz(aBLZBank);
  }

  /**
   * Feld A6 - Name des Auftraggebers. G�ltige Werte: Siehe zugelassene Zeichen,
   * max L�nge: 27 Standardwert: "" (leer)
   */
  public void setAKundenname(String aKundenname) throws DtausException
  {
    asatz.setKundenname(aKundenname);
  }

  /**
   * Feld A7 - Datum Format: TTMMJJ Standardwert: Tagesdatum
   */
  public void setADatum(String aDatum) throws DtausException
  {
    asatz.setDateierstellungsdatum(aDatum);
  }

  /**
   * Feld A9 - Kontonummer des Auftraggebers Standardwert: 0
   */
  public void setAKonto(long aKonto) throws DtausException
  {
    asatz.setKonto(aKonto);
  }

  /**
   * Feld A11b - Ausf�hrungsdatum Angabe freigestellt. Nicht j�nger als
   * Diskettenerstellungsdatum (Feld A7), jedoch h�chstens 15 Kalendertage �ber
   * dem Erstellungsdatum aus Feld A7. Soweit in diesem Datenfeld ein
   * Ausf�hrungstermin angegeben wird, ist zu beachten, dass der in Abs. II, Nr.
   * 3 der Sonderbedingungen f�r den Datentr�geraustausch genannte
   * Nachweiszeitraum von mindestens zehn Kalendertagen erst ab dem genannten
   * Ausf�hrungstermin zu berechnen ist.
   */
  public void setAAusfuehrungsdatum(Date aAusfuehrungsdatum)
  {
    asatz.setAusfuehrungsdatum(aAusfuehrungsdatum);
  }

  /**
   * Feld C3 - Bankleitzahl erstbeteiligtes Kreditinstitut, freigestellt
   */
  public void setCBLZErstbeteiligtesInstitut(long value) throws DtausException
  {
    csatz.setBlzErstbeteiligt(value);
  }

  /**
   * Feld C4 - Bankleitzahl endbeg�nstigtes Institut
   */
  public void setCBLZEndbeguenstigt(long value) throws DtausException
  {
    csatz.setBlzEndbeguenstigt(value);
  }

  /**
   * Feld C5 - Kontonummer des �berweisungsempf�ngers/Zahlungspflichtigen
   */
  public void setCKonto(long cKonto) throws DtausException
  {
    csatz.setKontonummer(cKonto);
  }

  /**
   * Feld C7 - Textschl�ssel - Kennzeichnung der Zahlungsart "51000"
   * �berweisungs-Gutschrift "53000" �berweisung Lohn/Gehalt/Rente "5400J"
   * Verm�genswirksame Leistung (VL) ohne Sparzulage "5400J" Verm�genswirksame
   * Leistung (VL) mit Sparzulage "56000" �berweisung �ffentlicher Kassen Die im
   * Textschl�ssel mit J bezeichnete Stelle, wird bei �bernahme in eine Zahlung
   * automatisch mit der jeweils aktuellen Jahresendziffer (7, wenn 97) ersetzt.
   */

  public void setCTextschluessel(int value) throws DtausException
  {
    csatz.setTextschluessel(value);
  }

  /**
   * Feld C12 - Betrag in Euro, wenn Auftragsw�hrung in den Feldern A12 und C17a =
   * "1"; sonst Nullen
   */
  public void setCBetragInCent(long cBetragInCent) throws DtausException
  {
    csatz.setBetragInCent(cBetragInCent);
  }

  public void setCBetragInEuro(double value) throws DtausException
  {
    csatz.setBetragInEuro(value);
  }

  /**
   * Feld C14a - Name des �berweisungsempf�ngers/Zahlungspflichtigen. Max. 27
   * Stellen
   */
  public void setCName(String value) throws DtausException
  {
    csatz.setNameEmpfaenger(value);
  }

  public void setCInterneKundennummer(long value) throws DtausException
  {
    csatz.setInterneKundennummer(value);
  }

  /**
   * Maximal 13 mal pro Buchungssatz aufrufbar
   */
  public void addCVerwendungszweck(String value) throws DtausException
  {
    csatz.addVerwendungszweck(value);
  }

  public void writeASatz() throws Exception
  {
    asatz.write(dos);
  }

  public void writeCSatz() throws IOException, DtausException
  {
    csatz.setNameAbsender(asatz.getKundenname());
    csatz.setErstbeauftragtesInstitut(asatz.getBlz());
    csatz.setKontoAuftraggeber(asatz.getKonto());
    esatz.add(csatz); // Kontrollsummen addieren
    csatz.write(dos);
    csatz = new CSatz();
  }

  public void writeESatz() throws IOException
  {
    esatz.write(dos);
  }

  public long getAnzahlSaetze()
  {
    return esatz.getAnzahlDatensaetze();
  }

  public BigInteger getSummeKontonummern()
  {
    return esatz.getSummeKontonummern();
  }

  public BigInteger getSummeBankleitzahlen()
  {
    return esatz.getSummeBankleitzahlen();
  }

  public BigInteger getSummeBetraege()
  {
    return esatz.getSummeBetraege();
  }
}
/*
 * $Log$
 * Revision 1.1  2006/06/05 09:34:51  jost
 * Neu
 *
 */
