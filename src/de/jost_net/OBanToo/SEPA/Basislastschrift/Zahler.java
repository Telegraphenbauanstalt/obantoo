package de.jost_net.OBanToo.SEPA.Basislastschrift;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

import de.jost_net.OBanToo.SEPA.BIC;
import de.jost_net.OBanToo.SEPA.IBAN;
import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.StringLatin.Zeichen;

public class Zahler
{
  private String mandatid;

  private Date mandatdatum;

  private String bic;

  private String name;

  private String iban;

  private String verwendungszweck;

  private BigDecimal betrag;

  private static final BigDecimal nu = new BigDecimal("0.00");

  /**
   * Gibt die Mandats-ID zur�ck
   * 
   * @return Mandats-ID
   * @throws SEPAException
   */
  public String getMandatid() throws SEPAException
  {
    checkMandatID(mandatid);
    return mandatid;
  }

  /**
   * Mandats-ID setzen. Max. 35 Stellen.
   * 
   * @param mandatid
   * @throws SEPAException
   */
  public void setMandatid(String mandatid) throws SEPAException
  {
    checkMandatID(mandatid);
    this.mandatid = mandatid;
  }

  private void checkMandatID(String mandatid) throws SEPAException
  {
    if (mandatid == null || mandatid.length() == 0 || mandatid.length() > 35)
    {
      throw new SEPAException("Ung�ltige Mandat-ID: " + mandatid);
    }
  }

  /**
   * Datum des Mandats zur�ckgeben.
   * 
   * @return Mandats-Datum
   * @throws SEPAException
   */
  public Date getMandatdatum() throws SEPAException
  {
    checkMandatdatum(mandatdatum);
    return mandatdatum;
  }

  /**
   * Datum des Mandats setzen.
   * 
   * @param mandatdatum
   * @throws SEPAException
   */
  public void setMandatdatum(Date mandatdatum) throws SEPAException
  {
    checkMandatdatum(mandatdatum);
    this.mandatdatum = mandatdatum;
  }

  private void checkMandatdatum(Date mandatdatum) throws SEPAException
  {
    if (mandatdatum == null || mandatdatum.after(new Date()))
    {
      throw new SEPAException("Ung�ltiges Mandatdatum: " + mandatdatum);
    }
  }

  /**
   * BIC zur�ckgeben
   * 
   * @return BIC
   * @throws SEPAException
   */
  public String getBic() throws SEPAException
  {
    checkBic(bic);
    return bic;
  }

  /**
   * BIC setzen. L�nge 8 oder 11 Stellen
   * 
   * @param bic
   * @throws SEPAException
   */
  public void setBic(String bic) throws SEPAException
  {
    checkBic(bic);
    this.bic = bic;
  }

  private void checkBic(String bic) throws SEPAException
  {
    new BIC(bic);
  }

  /**
   * Name des Zahlungspflichtigen zur�ckgeben
   * 
   * @return Name des Zahlungspflichtigen
   * @throws SEPAException
   */
  public String getName() throws SEPAException
  {
    checkName(name);
    return name;
  }

  /**
   * Name des Zahlungspflichtigen setzen. L�nge max. 70 Stellen.
   * 
   * @param name
   * @throws SEPAException
   */
  public void setName(String name) throws SEPAException
  {
    String tmpName = Zeichen.convert(name);
    checkName(tmpName);
    this.name = tmpName;
  }

  private void checkName(String name) throws SEPAException
  {
    if (name == null || name.length() == 0 || name.length() > 70)
    {
      throw new SEPAException("Ung�ltiger Name: " + name);
    }
  }

  /**
   * BIC zur�ckgeben.
   * 
   * @return BIC
   * @throws SEPAException
   */
  public String getIban() throws SEPAException
  {
    new IBAN(iban);
    return iban;
  }

  /**
   * BIC setzen. L�nge abh�ngig vom Land.
   * 
   * @param iban
   * @throws SEPAException
   */
  public void setIban(String iban) throws SEPAException
  {
    new IBAN(iban);
    this.iban = iban;
  }

  /**
   * Unstrukturierten Verwendungszweck zur�ckgeben.
   * 
   * @return Unstrukturierten Verwendungszweck
   * @throws SEPAException
   */
  public String getVerwendungszweck() throws SEPAException
  {
    checkVerwendungszweck(verwendungszweck);
    return verwendungszweck;
  }

  /**
   * Unstrukturierten Verwendungszweck setzen. L�nge max. 70 Stellen.
   * 
   * @param verwendungszweck
   * @throws SEPAException
   */
  public void setVerwendungszweck(String verwendungszweck) throws SEPAException
  {
    String tmpVerwendungszweck = Zeichen.convert(verwendungszweck);
    checkVerwendungszweck(tmpVerwendungszweck);
    this.verwendungszweck = tmpVerwendungszweck;
  }

  private void checkVerwendungszweck(String verwendungszweck)
      throws SEPAException
  {
    if (verwendungszweck == null || verwendungszweck.length() == 0
        || verwendungszweck.length() > 140)
    {
      throw new SEPAException("Ung�ltiger Verwendungszweck: "
          + verwendungszweck);
    }
  }

  /**
   * Betrag zur�ckgeben.
   * 
   * @return Betrag
   * @throws SEPAException
   */
  public BigDecimal getBetrag() throws SEPAException
  {
    checkBetrag(betrag);
    return betrag;
  }

  /**
   * Betrag setzen. Wert muss > 0
   * 
   * @param betrag
   * @throws SEPAException
   */
  public void setBetrag(BigDecimal betrag) throws SEPAException
  {
    checkBetrag(betrag);
    this.betrag = betrag;
  }

  public void checkBetrag(BigDecimal betrag) throws SEPAException
  {
    if (betrag == null || betrag.compareTo(nu) == -1
        || betrag.compareTo(nu) == 0)
    {
      throw new SEPAException("Ung�ltiger Betrag: " + betrag);
    }
  }

  /**
   * Zusammenfassung von 2 Buchungen zu einer Mandats-ID zu einer.
   * 
   * @param zahler
   * @throws SEPAException
   */
  public void add(Zahler zahler) throws SEPAException
  {
    if (verwendungszweck == null)
    {
      verwendungszweck = "";
    }
    String tmpverwendungszweck = verwendungszweck + " " + betrag.toString()
        + ", " + zahler.getVerwendungszweck() + " " + zahler.getBetrag();
    if (tmpverwendungszweck.length() > 140)
    {
      throw new SEPAException(
          "Zahlung kann nicht zusammengef�gt werden. Verwendungszweck ist zu lang");
    }
    verwendungszweck = tmpverwendungszweck;
    betrag = betrag.add(zahler.getBetrag());
  }

  @Override
  public String toString()
  {
    String message = "";
    try
    {
      message = MessageFormat.format(
          "Zahler: Name={0}, IBAN={1}, BIC={2}, Verwendungszweck={3}, Betrag={4}, "
              + "Mandatdatum={5}, Mandatreferenz={6}", getName(), getIban(),
          getBic(), getVerwendungszweck(), getBetrag(), getMandatdatum(),
          getMandatid());
    }
    catch (SEPAException e)
    {
      message = e.getMessage();
    }
    return message;
  }
}
