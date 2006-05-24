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

/**
 * A-Satz - Datei-Vorsatz
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
public class ASatz extends Satz
{
  /**
   * Feld a01, 4 Bytes, numerisch, Satzl�ngenfeld, Konstant 0128
   */
  private String aSatzlaenge = "0128";

  /**
   * Feld a02, 1 Byte, alpha, Satzart, Konstant A
   */
  private String aSatzart = "A";

  /**
   * Feld a03, 2 Byte, alpha, Kennzeichen GK oder LK, Hinweis auf Gutschriften
   * (G) bzw. Lastschriften (L), K = Kundendatei
   */
  private String aGutschriftLastschrift = null;

  /**
   * Feld a04, 8 Byte, numerisch, Bankleitzahl, Bankleitzahl des Kreditinstituts
   * (Dateiempf�nger)
   */
  private long aBlz = 0;

  /**
   * Feld a06, 27 Byte, alpha, Kundenname, Dateiabsender
   */
  private String aKundenname = null;

  /**
   * Feld a07, 6 Byte, numerisch, Datum, Dateierstellungsdatum (TTMMJJ)
   */
  private String aDateierstellungsdatum = null;

  /**
   * Feld a09, 10 Byte, numerisch, Kontonummer. Empf�nger/Absender Kunde, max 10
   * Stellen. �ber dieses Konto wird der Gegenwert verrechnet.
   */
  private long aKonto = 0;

  /**
   * Feld a10, 10 Byte, numerisch, Referenznummer des Einreichers, Angabe
   * freigestellt
   */
  private String aReferenz = "          ";

  /**
   * Feld a11b, 8 Byte, alpha, Ausf�hrungsdatum (TTMMJJJJ) Angabe freigestellt.
   * Nicht j�nger als Dateierstellungsdatum (Feld A7), jedoch h�chstens 15
   * Kalendertage �ber Erstellungsdatum aus Feld A7. Soweit in diesem Datenfeld
   * ein Ausf�hrungstermin angegeben wird, ist zu beachten, dass der in den
   * Sonderbedingungen genannte Nachweiszeitraum von mindestens 10 Kalendertagen
   * erst ab dem genannten Ausf�hrungstermin zu berechnen ist.
   */
  private String aAusfuehrungsdatum = null;

  /**
   * Feld a12, 1 Byte, alpha, W�hrungskennzeichen, konstant '1'
   */
  private String aWaehrungskennzeichen = "1";

  /**
   * Konstruktor mit der �bergabe eines zu parsenden Satzes
   * 
   * @param satz
   */
  public ASatz(String satz) throws DtausException
  {
    super(satz);
    if (!satz.substring(0, 4).equals(aSatzlaenge))
    {
      throw new DtausException(DtausException.A_SATZLAENGENFELD_FEHLERHAFT);
    }
    if (!satz.substring(4, 5).equals(aSatzart))
    {
      throw new DtausException(DtausException.A_SATZART_FEHLERHAFT);
    }
    setGutschriftLastschrift(satz.substring(5, 7));
    setBlz(satz.substring(7, 15));
    setKundenname(satz.substring(23, 50));
    setDateierstellungsdatum(satz.substring(50, 56));
    setKonto(satz.substring(60, 70));
    setReferenz(satz.substring(70, 80));
    setAusfuehrungsdatum(satz.substring(80, 88));
    setWaehrungskennzeichen(satz.substring(127, 128));
  }

  public void setGutschriftLastschrift(String value) throws DtausException
  {
    if (value.equals("GK") || value.equals("LK"))
    {
      aGutschriftLastschrift = value;
    }
    else
    {
      throw new DtausException(
          DtausException.A_GUTSCHRIFT_LASTSCHRIFT_FEHLERHAFT);
    }
  }

  public String getGutschriftLastschrift()
  {
    return aGutschriftLastschrift;
  }

  public void setBlz(String value) throws DtausException
  {
    try
    {
      aBlz = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.A_BLZ_FEHLERHAFT);
    }
  }

  public void setBlz(long value)
  {
    aBlz = value;
  }

  public long getBlz()
  {
    return aBlz;
  }

  public void setKundenname(String value)
  {
    aKundenname = value.trim();
  }

  public String getKundenname()
  {
    return aKundenname;
  }

  public void setDateierstellungsdatum(String value) throws DtausException
  {
    aDateierstellungsdatum = value;
  }

  public String getDateierstellungsdatum()
  {
    return aDateierstellungsdatum;
  }

  public void setKonto(String value) throws DtausException
  {
    try
    {
      aKonto = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.A_KONTO_FEHLERHAFT);
    }
  }

  public void setKonto(long value)
  {
    aKonto = value;
  }

  public long getKonto()
  {
    return aKonto;
  }

  public void setReferenz(String value)
  {
    aReferenz = value;
  }

  public String getReferenz()
  {
    return aReferenz;
  }

  public void setAusfuehrungsdatum(String value)
  {
    aAusfuehrungsdatum = value;
  }

  public String getAusfuehrungsdatum()
  {
    return aAusfuehrungsdatum;
  }

  public void setWaehrungskennzeichen(String value) throws DtausException
  {
    if (value.equals("1"))
    {
      aWaehrungskennzeichen = value;
    }
    else
    {
      throw new DtausException(
          DtausException.A_WAEHRUNGSKENNZEICHEN_FEHLERHAFT, value);
    }

  }

  public String toString()
  {
    return "Satzlaenge=" + aSatzlaenge + ", Satzart=" + aSatzart
        + ", Gutschrift/Lastschrift=" + aGutschriftLastschrift + ", BLZ="
        + aBlz + ", Kundenname=" + aKundenname + ", Dateierstellungsdatum="
        + aDateierstellungsdatum + ", Konto=" + aKonto + ", Referenz="
        + aReferenz + ", Ausf�hrungsdatum=" + aAusfuehrungsdatum
        + ", W�hrungskennzeichen=" + aWaehrungskennzeichen;
  }
}
/*
 * $Log$
 * Revision 1.1  2006/05/24 16:24:44  jost
 * Prerelease
 *
 */

