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

public class Satz
{
  public Satz() throws DtausException
  {
    //
  }

  public Satz(String value) throws DtausException
  {
    validCharacters(value);
  }

  protected String umkodierung(String value)
  {
    value = value.replace('[', '�');
    return value;
  }

  protected void validCharacters(String value) throws DtausException
  {
    for (int i = 0; i < value.length(); i++)
    {
      char c = value.charAt(i);
      if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || c == ' '
          || c == '.' || c == ',' || c == '&' || c == '-' || c == '+'
          || c == '*' || c == '%' || c == '/' || c == '$' || c == 0x5b
          || c == 0x5c || c == 0x5d || c == 0x7e)
      {
        // g�ltig
      }
      else
      {
        throw new DtausException(DtausException.UNGUELTIGES_ZEICHEN, value
            .substring(i, i + 1));
      }
    }
  }

  private String makeValid(String value)
  {
    String ret = value.toUpperCase();
    ret = ret.replace('�', (char) 0x5b);
    ret = ret.replace('�', (char) 0x5b);
    ret = ret.replace('�', (char) 0x5c);
    ret = ret.replace('�', (char) 0x5c);
    ret = ret.replace('�', (char) 0x5d);
    ret = ret.replace('�', (char) 0x5d);
    ret = ret.replace('�', (char) 0x7e);
    return ret;
  }

  /**
   * Datenfelder auf die L�nge 27 bringen
   */
  public String make27(String in)
  {
    String out = "";
    if (in.length() > 27)
    {
      out = in.substring(0, 27);
    }
    if (in.length() < 27)
    {
      out = in + Tool.space(27 - in.length());
    }
    out = makeValid(out);
    return out;
  }

}
/*
 * $Log$
 * Revision 1.2  2006/06/05 09:35:59  jost
 * Erweiterungen f. d. DtausDateiWriter
 * Revision 1.1 2006/05/24 16:24:44 jost Prerelease
 * 
 */
