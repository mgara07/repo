package tn.esprit.softib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.service.ExcelService;
import tn.esprit.softib.service.IFormulaireService;

@RestController
@RequestMapping("/api/unauth")
public class UnAuthController {

	@Autowired
	IFormulaireService formulaireService;
	
	@Autowired
	ExcelService fileService;
	
	@PostMapping("/createFormulaire")
	@ResponseBody
	//@PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
	public Formulaire save(@RequestBody Formulaire formulaire){
		Formulaire form = formulaireService.addFormulaire(formulaire);
		return form;
	}
	
	
}
