package tn.esprit.softib.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.enums.FormStatus;
import tn.esprit.softib.enums.Gender;
import tn.esprit.softib.enums.Nature;
import tn.esprit.softib.enums.Type;

public class ExcelHelper {
	private static final String FAIL_UPLOAD = "fail to parse Excel file: ";
	private static final String FAIL_EXPORT = "fail to export data to Excel file: ";
	private static final String FILENAME = "formulaires.xlsx";
	private static final String FOLDER_EXPORT = "C:\\Users\\majallouz\\3ALINFO3\\";
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Cin", "FirstName", "LastName", "Phone", "Gender", "Adresse", "Email", "NatureCompte",
			"SalaireNet", "Job", "Type" };
	static String[] HEADERsEXPORT = { "Cin", "FirstName", "LastName", "Phone", "Gender", "Adresse", "Email", "NatureCompte",
			"SalaireNet", "Job", "Type","Status" };
	static String SHEET = "formulaires";

	public static boolean hasExcelFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<Formulaire> excelToFormulaires(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			List<Formulaire> formulaires = new ArrayList<Formulaire>();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
			//	 skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Formulaire formulaire = new Formulaire();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						formulaire.setCin(String.valueOf((long)currentCell.getNumericCellValue()));
						break;
					case 1:
						formulaire.setFirstName(currentCell.getStringCellValue());
						break;
					case 2:
						formulaire.setLastName(currentCell.getStringCellValue());
						break;
					case 3:
						formulaire.setPhone((long)currentCell.getNumericCellValue());
						break;
					case 4:
						if ("HOMME".equals(currentCell.getStringCellValue().toUpperCase())) {
							formulaire.setGender(Gender.HOMME);
						} else if ("FEMME".equals(currentCell.getStringCellValue().toUpperCase())) {
							formulaire.setGender(Gender.FEMME);
						} else {
							formulaire.setGender(Gender.AUTRE);
						}
						break;
					case 5:
						formulaire.setAdresse(currentCell.getStringCellValue());
						break;
					case 6:
						formulaire.setEmail(currentCell.getStringCellValue());
						break;
					case 7:
						if ("COURANT".equals(currentCell.getStringCellValue().toUpperCase())) {
							formulaire.setNatureCompte(Nature.COURANT);
						} else {
							formulaire.setNatureCompte(Nature.EPARGNE);
						}
						break;
					case 8:
						formulaire.setSalaireNet((float)currentCell.getNumericCellValue());
						break;
					case 9:
						formulaire.setJob(currentCell.getStringCellValue());
						break;
					case 10: 
						if ("PHYSIQUE".equals(currentCell.getStringCellValue().toUpperCase())) {
							formulaire.setType(Type.PHYSIQUE);
						} else if("MORAL".equals(currentCell.getStringCellValue().toUpperCase())){
							formulaire.setType(Type.MORAL);
						}
						break;

					default:
						break;
					}
					cellIdx++;
				}
				formulaire.setFormStatus(FormStatus.PENDING);
				formulaires.add(formulaire);
			}
			workbook.close();
			return formulaires;
		} catch (IOException e) {
			throw new RuntimeException(FAIL_UPLOAD + e.getMessage());
		}
	}
	
	public static ByteArrayInputStream FormulairesToExcel(List<Formulaire> formulaires) {
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	      // Header
	      Row headerRow = sheet.createRow(0);
	      for (int col = 0; col < HEADERsEXPORT.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERsEXPORT[col]);
	      }
	      int rowIdx = 1;
	      for (Formulaire formulaire : formulaires) {
	        Row row = sheet.createRow(rowIdx++);
	        row.createCell(0).setCellValue(formulaire.getCin());
	        row.createCell(1).setCellValue(formulaire.getFirstName());
	        row.createCell(2).setCellValue(formulaire.getLastName());
	        row.createCell(3).setCellValue(formulaire.getPhone());
	        row.createCell(4).setCellValue(formulaire.getGender().toString());
	        row.createCell(5).setCellValue(formulaire.getAdresse());
	        row.createCell(6).setCellValue(formulaire.getEmail());
	        row.createCell(7).setCellValue(formulaire.getNatureCompte().toString());
	        row.createCell(8).setCellValue(formulaire.getSalaireNet());
	        row.createCell(9).setCellValue(formulaire.getJob());
	        row.createCell(10).setCellValue(formulaire.getType().toString());
	        row.createCell(11).setCellValue(formulaire.getFormStatus().toString());
	      }
	      workbook.write(out);
	      FileOutputStream out2 = new FileOutputStream(
	              new File(FOLDER_EXPORT+FILENAME));
	      workbook.write(out2);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException(FAIL_EXPORT + e.getMessage());
	    }
	  }
}