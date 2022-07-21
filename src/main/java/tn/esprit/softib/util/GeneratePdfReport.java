package tn.esprit.softib.util;



import org.hibernate.boot.model.naming.ImplicitAnyDiscriminatorColumnNameSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tn.esprit.softib.entity.Compte;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class GeneratePdfReport {
	@Value("${pdfDir}")
	private static String pdfDir;
	
	@Value("${reportFileName}")
	private static String reportFileName;
	
	@Value("${reportFileNameDateFormat}")
	private static String reportFileNameDateFormat;
	
	@Value("${localDateFormat}")
	private static String localDateFormat;
	
	@Value("${logoImgPath}")
	private static String logoImgPath;
	
	@Value("${logoImgScale}")
	private Float[] logoImgScale;
	
	@Value("${currencySymbol:}")
	private String currencySymbol;
	
	@Value("${table_noOfColumns}")
	private int noOfColumns;
	
	
	
	static List<String> columnNames = new ArrayList<>();
	
	private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
	private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

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
           // PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate()));
            document.open();
            addLogo(document);
            addDocTitle(document);
            //BufferedImage img = ImageIO.read(new ByteArrayInputStream(comptes.get(1).getData()));
            Image iTextImage = Image.getInstance(compte.getData());
            iTextImage.scalePercent(50, 30);
            iTextImage.setAlignment(Element.ALIGN_CENTER);
            document.add(iTextImage);
            createTable(document,4,compte);
			addFooter(document);
            //document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return new ByteArrayInputStream(out.toByteArray());
    }
	private static void addLogo(Document document) {
		try {	
			Image img = Image.getInstance("C:\\Users\\yboutrif\\Desktop\\MissionEntreprise\\PI-SoftIB\\softib\\Files\\Bank_Logo.JPG");
			img.scalePercent(10, 10);
			img.setAlignment(Element.ALIGN_RIGHT);
			document.add(img);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addDocTitle(Document document) throws DocumentException {
		
		Paragraph p1 = new Paragraph();
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph(reportFileName, COURIER));
		p1.setAlignment(Element.ALIGN_CENTER);
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph("Relevé d'identité bancaire" , COURIER_SMALL));
		leaveEmptyLine(p1, 3);

		document.add(p1);

	}
	
	private static void createTable(Document document, int noOfColumns,Compte compte) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 3);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(noOfColumns);
		
		for(int i=0; i<4; i++) {
			columnNames.add("Name");
			columnNames.add("Nature du compte");
			columnNames.add("code Bic");
			columnNames.add("Iban");
			PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.PINK);
			table.addCell(cell);
		}

		table.setHeaderRows(1);
		getDbData(table,compte);
		document.add(table);
	}
	
	private static void getDbData(PdfPTable table, Compte compte) {
		
		
		
			
			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			table.addCell(compte.getNomComplet());
			table.addCell(compte.getNatureCompte().toString());
			table.addCell(compte.getCodeBic());
			table.addCell(compte.getIban());
			
			System.out.println(compte.getNomComplet());
		
		
	}
	
	private static void addFooter(Document document) throws DocumentException {
		Paragraph p2 = new Paragraph();
		leaveEmptyLine(p2, 3);
		p2.setAlignment(Element.ALIGN_MIDDLE);
		p2.add(new Paragraph(
				"Merci de choisir notre banque", 
				COURIER_SMALL_FOOTER));
		p2.setAlignment(Element.ALIGN_CENTER);
		
		document.add(p2);
	}
	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	private static String getPdfNameWithDate() {
		String localDateString = LocalDateTime.now().toString();
		return pdfDir+reportFileName+"-"+localDateString+".pdf";
	}
}