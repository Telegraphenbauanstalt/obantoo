/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigef�gte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA;

public class IBANRet
{
  private String code = null;

  private String iban = null;

  private String bic = null;

  public IBANRet(String code, String iban, String bic)
  {
    this.code = code;
    this.iban = iban;
    this.bic = bic;
  }

  public IBANRet(String code)
  {
    this.code = code;
  }

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public String getIban()
  {
    return iban;
  }

  public String getBic()
  {
    return bic;
  }
}