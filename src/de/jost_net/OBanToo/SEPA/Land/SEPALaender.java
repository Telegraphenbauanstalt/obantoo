/**
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigef�gte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA.Land;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SEPALaender
{
  private static ArrayList<SEPALand> laender = new ArrayList<SEPALand>();

  private static HashMap<String, SEPALand> laendermap = new HashMap<String, SEPALand>();
  static
  {
    laender.add(getDeutschland());
    laender.add(getNiederlande());
    laender.add(getOesterreich());
    laender.add(getPolen());
    laender.add(getSchweiz());
    for (SEPALand land : laender)
    {
      laendermap.put(land.getKennzeichen(), land);
    }
  }

  public static SEPALand getLand(String kennzeichen)
  {
    return laendermap.get(kennzeichen);
  }

  public static ArrayList<SEPALand> getLaender()
  {
    return laender;
  }

  public Iterator<SEPALand> getIterator()
  {
    return laender.iterator();
  }

  private static SEPALand getDeutschland()
  {
    SEPALand deutschland = new SEPALand();
    deutschland.setKennzeichen("DE");
    deutschland.setBezeichnung("Deutschland");
    deutschland.setBankIdentifierLength(8);
    deutschland.setAccountLength(10);
    deutschland.setBankIdentifierSample("37040044");
    deutschland.setAccountSample("532013000");
    deutschland.setIBANSample("DE89370400440532013000");
    return deutschland;
  }

  private static SEPALand getNiederlande()
  {
    SEPALand niederlande = new SEPALand();
    niederlande.setKennzeichen("NL");
    niederlande.setBezeichnung("Niederlande");
    niederlande.setBankIdentifierLength(4);
    niederlande.setAccountLength(10);
    niederlande.setBankIdentifierSample("ABNA");
    niederlande.setAccountSample("0417164300");
    niederlande.setIBANSample("NL91ABNA0417164300");
    return niederlande;
  }

  private static SEPALand getOesterreich()
  {
    SEPALand oesterreich = new SEPALand();
    oesterreich.setKennzeichen("AT");
    oesterreich.setBezeichnung("�sterreich");
    oesterreich.setBankIdentifierLength(5);
    oesterreich.setAccountLength(11);
    oesterreich.setBankIdentifierSample("00762");
    oesterreich.setAccountSample("011623852957");
    oesterreich.setIBANSample("CH9300762011623852957");
    return oesterreich;
  }

  private static SEPALand getPolen()
  {
    SEPALand polen = new SEPALand();
    polen.setKennzeichen("PL");
    polen.setBezeichnung("Polen");
    polen.setBankIdentifierLength(8);
    polen.setAccountLength(16);
    polen.setBankIdentifierSample("10901014");
    polen.setAccountSample("0000071219812874");
    polen.setIBANSample("PL61109010140000071219812874");
    return polen;
  }

  private static SEPALand getSchweiz()
  {
    SEPALand schweiz = new SEPALand();
    schweiz.setKennzeichen("CH");
    schweiz.setBezeichnung("Schweiz");
    schweiz.setBankIdentifierLength(5);
    schweiz.setAccountLength(12);
    schweiz.setBankIdentifierSample("19043");
    schweiz.setAccountSample("234573201");
    schweiz.setIBANSample("AT611904300234573201");
    return schweiz;
  }
}
