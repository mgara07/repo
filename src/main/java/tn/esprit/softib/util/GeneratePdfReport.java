package tn.esprit.softib.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tn.esprit.softib.entity.Compte;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class GeneratePdfReport {

    private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

    public static ByteArrayInputStream comptesReport(Compte compte) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(60);
           // table.setWidths(new int[]{1, 5, 5});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
           

            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Rib", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("code bic", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Iban", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
       
         //   for (Compte compte : comptes) {

                PdfPCell cell;

         /*       cell = new PdfPCell(new Phrase(compte.getId()));
                //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);*/

                cell = new PdfPCell(new Phrase(compte.getNomComplet()));
                cell.setPaddingLeft(5);
               // cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
               // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(compte.getRib())));
               // cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
               // cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(compte.getCodeBic())));
               // cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              //  cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(compte.getIban())));
              //  cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
               // cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
   
             /*   Image iTextImage = Image.getInstance(comptes.get(1).getData());
                cell = new PdfPCell(new Phrase(String.valueOf(img)));
                //  cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 // cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                  cell.setPaddingRight(5);
                  table.addCell(cell);*/
           // }

            PdfWriter.getInstance(document, out);
            document.open();
            //BufferedImage img = ImageIO.read(new ByteArrayInputStream(comptes.get(1).getData()));
            Image iTextImage = Image.getInstance(compte.getData());
            document.add(iTextImage);
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return new ByteArrayInputStream(out.toByteArray());
    }
}