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

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.jost_net.OBanToo.Tools.Util;

/**
 * Ausgabe von DTAUS-Dateien im PDF-Format <br>
 * <p>
 * Mit dieser Klasse k�nnen DTAUS-Dateien ins PDF-Format konvertiert werden.
 * Sind in einer DTAUS-Datei mehrere logische Dateien enthalten, werden alle
 * logischen Dateien ausgegeben.
 * </p>
 * <p>
 * Die Klasse verf�gt �ber eine Main-Methode und kann daher auch stand-alone
 * genutzt werden. Der Aufruf erfolgt mit <i>java -cp ...
 * de.jost_net.OBanToo.Dtaus dtausfile pdffile</i>
 * </p>
 * <p>
 * Hinweis! F�r die PDF-Generierung wird iText ben�tigt. iText kann <a
 * href="http://www.lowagie.com/iText/download.html" target=blank>hier</a>
 * bezogen werden. Das iText.jar mu� sich im Classpath befinden.
 * </p>
 * 
 * 
 * 
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
public class Dtaus2Pdf
{

  public Dtaus2Pdf(String dtausfile, String pdffile) throws IOException,
      DtausException, DocumentException
  {
    DtausDateiParser in = new DtausDateiParser(dtausfile);
    Document doc = new Document();
    FileOutputStream out = new FileOutputStream(pdffile);

    PdfWriter writer = PdfWriter.getInstance(doc, out);
    doc.setMargins(80, 30, 20, 30); // links, rechts, oben, unten
    doc.addAuthor("OBanToo");
    doc.addTitle("DTAUS2PDF");

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String fuss = "Ausgegeben am " + sdf.format(new Date())
        + "           Seite:  ";
    HeaderFooter hf = new HeaderFooter();
    hf.setFooter(fuss);
    writer.setPageEvent(hf);

    doc.open();

    Paragraph pTitle = new Paragraph("DTAUS-Datei", FontFactory.getFont(
        FontFactory.HELVETICA_BOLD, 13));
    pTitle.setAlignment(Element.ALIGN_CENTER);

    int anzlogdat = in.getAnzahlLogischerDateien();
    for (int i = 1; i <= anzlogdat; i++)
    {
      in.setLogischeDatei(i);
      outputASatz(in, doc, i);
      PdfPTable table = new PdfPTable(5);
      float[] widths = { 120, 120, 60, 20, 50 };
      table.setWidths(widths);
      table.setWidthPercentage(100);
      table.setSpacingBefore(10);
      table.setSpacingAfter(0);

      String empfzahl = "";
      if (in.getASatz().getGutschriftLastschrift().startsWith("L"))
      {
        empfzahl = "Zahlungspflichtiger";
      }
      else
      {
        empfzahl = "Empf�nger";
      }
      table.addCell(getDetailCell(empfzahl, Element.ALIGN_CENTER,
          BaseColor.LIGHT_GRAY));
      table.addCell(getDetailCell("Verwendungszweck", Element.ALIGN_CENTER,
          BaseColor.LIGHT_GRAY));
      table.addCell(getDetailCell("Bankverbindung", Element.ALIGN_CENTER,
          BaseColor.LIGHT_GRAY));
      table.addCell(getDetailCell("Schl.", Element.ALIGN_CENTER,
          BaseColor.LIGHT_GRAY));
      table.addCell(getDetailCell("Betrag", Element.ALIGN_RIGHT,
          BaseColor.LIGHT_GRAY));
      table.setHeaderRows(1);

      CSatz c = in.next();
      while (c != null)
      {
        String name = c.getNameEmpfaenger();
        if (c.getInterneKundennummer() > 0)
        {
          name += "\n" + c.getInterneKundennummer();
        }
        table.addCell(getDetailCell(name, Element.ALIGN_LEFT));
        String vzweck = "";
        for (int i2 = 1; i2 <= c.getAnzahlVerwendungszwecke(); i2++)
        {
          vzweck += c.getVerwendungszweck(i2);
          if (i2 < c.getAnzahlVerwendungszwecke())
          {
            vzweck += "\n";
          }
        }
        table.addCell(getDetailCell(vzweck, Element.ALIGN_LEFT));
        table.addCell(getDetailCell(
            c.getBlzEndbeguenstigt() + "\n" + c.getKontonummer(),
            Element.ALIGN_LEFT));
        table.addCell(getDetailCell(c.getTextschluessel() + "",
            Element.ALIGN_LEFT));
        table.addCell(getDetailCell(
            de.jost_net.OBanToo.Tools.Util.formatCurrency(c.getBetragInEuro()),
            Element.ALIGN_RIGHT));
        c = in.next();
      }

      doc.add(table);
      outputESatz(in, doc);
    }
    doc.close();
    out.close();
  }

  private void outputASatz(DtausDateiParser in, Document doc, int logischeDatei)
      throws DocumentException
  {
    PdfPTable table = new PdfPTable(2);
    float[] widths = { 200, 170 };
    table.setWidths(widths);
    table.setWidthPercentage(65);
    table.setSpacingBefore(10);
    table.setSpacingAfter(0);
    table.setHorizontalAlignment(Element.ALIGN_RIGHT);

    table.addCell(getDetailCell("Logische Datei", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(logischeDatei + "", Element.ALIGN_LEFT));

    table.addCell(getDetailCell("Kundenname", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getASatz().getKundenname(),
        Element.ALIGN_LEFT));
    table.addCell(getDetailCell("BLZ", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table
        .addCell(getDetailCell(in.getASatz().getBlz() + "", Element.ALIGN_LEFT));
    table.addCell(getDetailCell("Konto", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getASatz().getKonto() + "",
        Element.ALIGN_LEFT));
    table.addCell(getDetailCell(
        "Gutschrift (G)/Lastschrift(L)/Bank(B)/Kunde(K)", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getASatz().getGutschriftLastschrift(),
        Element.ALIGN_LEFT));
    table.addCell(getDetailCell("Ausf�hrungsdatum", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getASatz().getAusfuehrungsdatumString(),
        Element.ALIGN_LEFT));
    table.addCell(getDetailCell("Erstellungsdatum", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getASatz().getDateierstellungsdatum(),
        Element.ALIGN_LEFT));
    doc.add(table);
  }

  private void outputESatz(DtausDateiParser in, Document doc)
      throws DocumentException
  {
    PdfPTable table = new PdfPTable(2);
    float[] widths = { 150, 200 };
    table.setWidths(widths);
    table.setWidthPercentage(60);
    table.setSpacingBefore(10);
    table.setSpacingAfter(0);
    table.setHorizontalAlignment(Element.ALIGN_RIGHT);

    table.addCell(getDetailCell("Summe Betr�ge", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(Util.formatCurrency(in.getESatz()
        .getSummeBetraege().doubleValue() / 100), Element.ALIGN_RIGHT));
    table.addCell(getDetailCell("Anzahl Datens�tze", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getESatz().getAnzahlDatensaetze() + "",
        Element.ALIGN_RIGHT));
    table.addCell(getDetailCell("Summe Bankleitzahlen", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getESatz().getSummeBankleitzahlen() + "",
        Element.ALIGN_RIGHT));
    table.addCell(getDetailCell("Summe Kontonummern", Element.ALIGN_RIGHT,
        BaseColor.LIGHT_GRAY));
    table.addCell(getDetailCell(in.getESatz().getSummeKontonummern() + "",
        Element.ALIGN_RIGHT));
    doc.add(table);
  }

  /**
   * Erzeugt eine Zelle der Tabelle.
   * 
   * @param text
   *          der anzuzeigende Text.
   * @param align
   *          die Ausrichtung.
   * @return die erzeugte Zelle.
   */
  private PdfPCell getDetailCell(String text, int align)
  {
    return getDetailCell(text, align, BaseColor.WHITE);
  }

  /**
   * Erzeugt eine Zelle der Tabelle.
   * 
   * @param text
   *          der anzuzeigende Text.
   * @param align
   *          die Ausrichtung.
   * @param backgroundcolor
   *          die Hintergundfarbe.
   * @return die erzeugte Zelle.
   */
  private PdfPCell getDetailCell(String text, int align,
      BaseColor backgroundcolor)
  {
    PdfPCell cell = new PdfPCell(new Phrase(notNull(text), FontFactory.getFont(
        FontFactory.HELVETICA, 8)));
    cell.setHorizontalAlignment(align);
    cell.setBackgroundColor(backgroundcolor);
    return cell;
  }

  /**
   * Erzeugt eine Zelle fuer die uebergebene Zahl.
   * 
   * @param value
   *          die Zahl.
   * @return die erzeugte Zelle.
   */
  // private PdfPCell getDetailCell(double value)
  // {
  // Font f = null;
  // if (value >= 0)
  // f = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL,
  // Color.BLACK);
  // else
  // f = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, Color.RED);
  // PdfPCell cell = new PdfPCell(new Phrase(new DecimalFormat("###,###,##0.00")
  // .format(value), f));
  // cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
  // return cell;
  // }

  /**
   * Gibt einen Leerstring aus, falls der Text null ist.
   * 
   * @param text
   *          der Text.
   * @return der Text oder Leerstring - niemals null.
   */
  private String notNull(String text)
  {
    return text == null ? "" : text;
  }

  public static void main(String[] args)
  {
    if (args.length != 2)
    {
      System.err
          .println("Usage: java -cp ... de.jost_net.OBanToo.Dtaus dtausfile pdffile");
      System.exit(1);
    }
    try
    {
      new Dtaus2Pdf(args[0], args[1]);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.exit(2);
    }
    catch (DtausException e)
    {
      e.printStackTrace();
      System.exit(3);
    }
    catch (DocumentException e)
    {
      e.printStackTrace();
      System.exit(4);
    }
  }
}
