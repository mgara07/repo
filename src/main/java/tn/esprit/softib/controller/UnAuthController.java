package tn.esprit.softib.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.service.ExcelService;
import tn.esprit.softib.service.ICompteService;
import tn.esprit.softib.service.IFormulaireService;
import tn.esprit.softib.util.GeneratePdfReport;
import tn.esprit.softib.util.QRCodeGenerator;

@RestController
@RequestMapping("/api/unauth")
public class UnAuthController {

	@Autowired
	IFormulaireService formulaireService;
	
	@Autowired
	ExcelService fileService;
	@Autowired
	ICompteService compteService;
	
	@PostMapping("/createFormulaire")
	@ResponseBody
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	public Formulaire save(@RequestBody Formulaire formulaire){
		Formulaire form = formulaireService.addFormulaire(formulaire);
		return form;
	}
	
	/*
	 * Yasmine Boutrif
	 * */
	
    @RequestMapping(value = "/pdfreport/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> compteReport(@PathVariable("id") Long id) throws MalformedURLException, IOException, DocumentException {
    	
    	
    	Compte compte = compteService.getCompteById(id);

    	List<Compte> comptes = (List<Compte>) compteService.getAllComptes();
    	

        ByteArrayInputStream bis = GeneratePdfReport.comptesReport(compte);
    	//ByteArrayInputStream bis = GeneratePdfReport..generatePdfReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

       return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
        
    }
    /*
     * Yasmine Boutrif
     * */
    @GetMapping(value = "/genrateQRCode/{id}/{width}/{height}")
   	public ResponseEntity<byte[]> generateQRCode(
   			@PathVariable("id") long id,
   			@PathVariable("width") Integer width,
   			@PathVariable("height") Integer height)
   		    throws Exception {
   				Compte compte = compteService.getCompteById(id);
   		        return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.getQRCodeImage(compte.getRib(), width, height));
   		    }
	
}
