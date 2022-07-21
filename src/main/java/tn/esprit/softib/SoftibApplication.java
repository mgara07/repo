package tn.esprit.softib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.ApplicationContext;

import tn.esprit.softib.utility.PDFGenerator;


@SpringBootApplication
@EnableScheduling
public class SoftibApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(SoftibApplication.class, args);
		
		PDFGenerator pDFGenerator = ac.getBean("pdfGenerator",PDFGenerator.class);
		
		pDFGenerator.generatePdfReport();
	}

}
