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

import java.util.Vector;

/**
 * C-Satz - Zahlungsaustauschsatz
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
// todo S�tze mit mehr als 3 Erweiterungsteilen testen
public class CSatz extends Satz
{
  /**
   * Feld c01, 4 Byte, numerisch, Satzl�nge, konstanter Teil 187 Bytes +
   * Erweiterungsteil( e) zu 29 Bytes, max. 0622 Stellen
   */
  private int cSatzlaenge = 0;

  /**
   * Feld c02, 1 Byte, alpha, Satzart, konstant 'C'
   */
  private String cSatzart = "C";

  /**
   * Feld c03, 8 Bytes, numerisch, Bankleitzahl, erstbeteiligtes Kreditinstitut,
   * freigestellt
   */
  private long cBlzErstbeteiligt = 0;

  /**
   * Feld c04, 8 Bytes, numerisch, Bankleitzahl, endbeg�nstigtes
   * Kreditinstitut/Zahlstelle
   */
  private long cBlzEndbeguenstigt = 0;

  /**
   * Feld c05, 10 Bytes, numerisch, Kontonummer,
   * �berweisungsempf�nger/Zahlungspflichtiger, rechtsb�ndig, nicht belegte
   * Stellen 0
   */
  private long cKonto = 0;

  /**
   * Feld c06, 13 Bytes, numerisch, interne Kundennummer, 1. Byte = 0, 2.-12.
   * Byte = interne Kundennummer oder Nullen, 13. Byte = 0
   */
  private long cInterneKundennummer = 999999999990l;

  /**
   * Feld c07a, 2 Bytes, numerisch, Textschl�ssel, Kennzeichnung der Zahlungsart
   * und Textschl�sselerg�nzungen
   */
  private int cTextschluessel;

  /**
   * Feld c07b, 3 Bytes, numerisch, Textschl�sselerg�nzung
   */
  private int cTextschluesselergaenzung;

  /**
   * Feld c10, 8 Bytes, numerisch, erstbeauftragtes Institut/erste Inkassostelle
   */
  private long cErstbeauftragtesInstitut = 0;

  /**
   * Feld c11, 10 Bytes, numerisch, Auftraggeber/Zahlungsempf�nger,
   * rechtsb�ndig, nicht belegte Stellen 0
   */
  private long cKontoAuftraggeber = 0;

  /**
   * Feld c12, 11 Bytes, numerisch, Betrag in Euro einschl. Nachkommastellen
   * (m��te eigentlich Betrag in Cent hei�en)
   */
  private long cBetrag = 0;

  /**
   * Feld c14a, 27 Bytes, alpha, Name,
   * �berweisungsempf�nger/Zahlungspflichtiger, linksb�ndig
   */
  private String cNameEmpfaenger = null;

  /**
   * Feld c15, 27 Bytes, alpha, Name, �berweisender/Zahlungsempf�nger
   * (linksb�ndig), es sind m�glichst kurze Bezeichnungen zu verwenden
   */
  private String cNameAbsender = null;

  /**
   * Feld c16, 27 Bytes, alpha, Verwendungszweck, Es sind m�glichst kurze
   * Angaben zu machen. Linksb�ndig sind solche Angaben unterzubringen, auf die
   * der Beg�nstigte bei �berweisungen m�glicherweise zuzugreifen beabsichtigt
   * (z. B. Bausparkonto, Versicherungs-, Rechnungsnummer) oder die der
   * Zahlungsempf�nger bei Lastschriften ben�tigt, falls die Zahlung als
   * unbezahlt bzw. unanbringlich zur�ckgeleitet wird.
   */
  private String cVerwendungszweck = null;

  /**
   * Feld 17a, 1 Byte, alpha, W�hrungskennzeichen, konstant "1"
   */
  private String cWaehrungskennzeichen = "1";

  /**
   * Feld 18, 2 Bytes, numerisch, Erweiterungszeichen, 00 = es folgt kein
   * Erweiterungsteil, 01-15 = Anzahl der Erweiterungsteile
   */
  private int cErweiterungszeichen = 0;

  /**
   * Felder 19 und 20, 2 Stellen Kennzeichen des Erweiterungsteils, 01 = Names
   * des Beg�nstigten/Zahlungsempf�ngers, 02 = Verwendungszweck, 03 = Name des
   * �berweisenden bzw. Zahlungsempf�ngers und 27 Stellen f�r die Erweiterung
   */
  private Vector cErweiterung01 = new Vector();

  private Vector cErweiterung02 = new Vector();

  private Vector cErweiterung03 = new Vector();

  /**
   * Konstruktor mit der �bergabe eines zu parsenden Satzes
   */
  public CSatz(String satz) throws DtausException
  {
    super(satz);
    satz = umkodierung(satz);
    checkSatzlaengenfeld(satz.substring(0, 4));
    if (!satz.substring(4, 5).equals(cSatzart))
    {
      throw new DtausException(DtausException.C_SATZART_FEHLERHAFT, satz
          .substring(4, 5));
    }
    setBlzErstbeteiligt(satz.substring(5, 13));
    setBlzEndbeguenstigt(satz.substring(13, 21));
    setKontonummer(satz.substring(21, 31));
    setInterneKundennummer(satz.substring(32, 44));
    setTextschluessel(satz.substring(44, 46));
    setTextschluesselergaenzung(satz.substring(46, 49));
    setErstbeauftragtesInstitut(satz.substring(61, 69));
    setKontoAuftraggeber(satz.substring(69, 79));
    setBetrag(satz.substring(79, 90));
    setNameEmpfaenger(satz.substring(93, 120));
    setNameAbsender(satz.substring(128, 155));
    setVerwendungszweck(satz.substring(155, 182));
    setWaehrungskennzeichen(satz.substring(182, 183));
    setErweiterungskennzeichen(satz.substring(185, 187));
    if (getSatzlaenge() != 187 + (getErweiterungszeichen() * 29))
    {
      throw new DtausException(DtausException.C_SATZLAENGE_FEHLERHAFT, satz
          .substring(0, 4));
    }
    if (this.getErweiterungszeichen() >= 1)
    {
      addErweiterung(satz.substring(187, 216));
    }
    if (this.getErweiterungszeichen() >= 2)
    {
      addErweiterung(satz.substring(216, 245));
    }
    if (this.getErweiterungszeichen() >= 3)
    {
      addErweiterung(satz.substring(245, 274));
    }
  }

  private void checkSatzlaengenfeld(String value) throws DtausException
  {
    try
    {
      cSatzlaenge = Integer.parseInt(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_SATZLAENGE_FEHLERHAFT, value);
    }
    if (cSatzlaenge < 187 || cSatzlaenge > 622)
    {
      throw new DtausException(DtausException.C_SATZLAENGE_FEHLERHAFT, value);
    }
  }

  public int getSatzlaenge()
  {
    return cSatzlaenge;
  }

  public void setBlzErstbeteiligt(String value) throws DtausException
  {
    try
    {
      cBlzErstbeteiligt = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_BLZERSTBETEILIGT_FEHLERHAFT,
          value);
    }
  }

  public long getBlzErstbeteiligt()
  {
    return cBlzErstbeteiligt;
  }

  public void setBlzEndbeguenstigt(String value) throws DtausException
  {
    try
    {
      cBlzEndbeguenstigt = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_BLZENDBEGUENSTIGT_FEHLERHAFT,
          value);
    }
  }

  public long getBlzEndbeguenstigt()
  {
    return cBlzEndbeguenstigt;
  }

  public void setKontonummer(String value) throws DtausException
  {
    try
    {
      cKonto = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_KONTONUMMER_FEHLERHAFT, value);
    }
  }

  public long getKontonummer()
  {
    return cKonto;
  }

  public void setInterneKundennummer(String value) throws DtausException
  {
    try
    {
      cInterneKundennummer = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_INTERNEKUNDENNUMMER_FEHLERHAFT,
          value);
    }
  }

  public long getInterneKundennummer()
  {
    return cInterneKundennummer;
  }

  public void setTextschluessel(String value) throws DtausException
  {
    try
    {
      cTextschluessel = Integer.parseInt(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_TEXTSCHLUESSEL_FEHLERHAFT,
          value);
    }
  }

  public long getTextschluessel()
  {
    return cTextschluessel;
  }

  public void setTextschluesselergaenzung(String value) throws DtausException
  {
    try
    {
      cTextschluesselergaenzung = Integer.parseInt(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(
          DtausException.C_TEXTSCHLUESSELERGAENZUNG_FEHLERHAFT, value);
    }
  }

  public long getTextschluesselergaenzung()
  {
    return cTextschluesselergaenzung;
  }

  public void setErstbeauftragtesInstitut(String value) throws DtausException
  {
    try
    {
      cErstbeauftragtesInstitut = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(
          DtausException.C_ERSTBEAUFTRAGTESINSTITUT_FEHLERHAFT, value);
    }
  }

  public long getErstbeauftragtesInstitut()
  {
    return cErstbeauftragtesInstitut;
  }

  public void setKontoAuftraggeber(String value) throws DtausException
  {
    try
    {
      cKontoAuftraggeber = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_KONTOAUFTRAGGEBER_FEHLERHAFT,
          value);
    }
  }

  public long getKontoAuftraggeber()
  {
    return cKontoAuftraggeber;
  }

  public void setBetrag(String value) throws DtausException
  {
    try
    {
      cBetrag = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_BETRAG_FEHLERHAFT, value);
    }
  }

  public long getBetrag()
  {
    return cBetrag;
  }

  public void setNameEmpfaenger(String value) throws DtausException
  {
    cNameEmpfaenger = value.trim();
  }

  public String getNameEmpfaenger()
  {
    return cNameEmpfaenger;
  }

  public void setNameAbsender(String value) throws DtausException
  {
    cNameAbsender = value.trim();
  }

  public String getNameAbsender()
  {
    return cNameAbsender;
  }

  public void setVerwendungszweck(String value) throws DtausException
  {
    cVerwendungszweck = value.trim();
  }

  public String getVerwendungszweck()
  {
    return cVerwendungszweck;
  }

  public void setWaehrungskennzeichen(String value) throws DtausException
  {
    if (value.equals("1"))
    {
      cWaehrungskennzeichen = value;
    }
    else
    {
      throw new DtausException(
          DtausException.C_WAEHRUNGSKENNZEICHEN_FEHLERHAFT, value);
    }
  }

  public String getWaehrungskennzeichen()
  {
    return cWaehrungskennzeichen;
  }

  public void setErweiterungskennzeichen(String value) throws DtausException
  {
    try
    {
      cErweiterungszeichen = Integer.parseInt(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.C_ERWEITERUNGSZEICHEN_FEHLERHAFT,
          value);
    }
  }

  public int getErweiterungszeichen()
  {
    return cErweiterungszeichen;
  }

  public void addErweiterung(String value) throws DtausException
  {
    String val = value.substring(2).trim();
    if (value.startsWith("01"))
    {
      this.cErweiterung01.addElement(val);
    }
    else if (value.startsWith("02"))
    {
      this.cErweiterung02.addElement(val);
    }
    else if (value.startsWith("03"))
    {
      this.cErweiterung03.addElement(val);
    }
    else
    {
      throw new DtausException(DtausException.C_ERWEITERUNG_FEHLERHAFT, value);
    }
  }

  public String toString()
  {
    String ret = "Satzl�nge=" + this.getSatzlaenge() + ", BLZ erstbeteiligt="
        + this.getBlzErstbeteiligt() + ", BLZ endbeg�nstigt="
        + this.getBlzEndbeguenstigt() + ", Kontonummer="
        + this.getKontonummer() + ", interne Kundennummer="
        + this.getInterneKundennummer() + ", Textschluessel="
        + this.getTextschluessel() + ", Textschluesselerg�nzung="
        + this.getTextschluesselergaenzung() + ", erstbeauftragtes Institut="
        + this.getErstbeauftragtesInstitut() + ", Konto Auftraggeber="
        + this.getKontoAuftraggeber() + ", Betrag=" + this.getBetrag()
        + ", Name Empf�nger=" + this.getNameEmpfaenger() + ", Name Absender="
        + this.getNameAbsender() + ", Verwendungszweck="
        + this.getVerwendungszweck() + ", W�hrungskennzeichen="
        + this.getWaehrungskennzeichen() + ", Erweiterungszeichen="
        + this.getErweiterungszeichen();
    for (int i = 0; i < this.cErweiterung01.size(); i++)
    {
      ret += ", Erweiterung=" + this.cErweiterung01.elementAt(i);
    }
    for (int i = 0; i < this.cErweiterung02.size(); i++)
    {
      ret += ", Erweiterung=" + this.cErweiterung02.elementAt(i);
    }
    for (int i = 0; i < this.cErweiterung03.size(); i++)
    {
      ret += ", Erweiterung=" + this.cErweiterung03.elementAt(i);
    }
    return ret;
  }
}
/*
 * $Log$
 * Revision 1.1  2006/05/24 16:24:44  jost
 * Prerelease
 *
 */
