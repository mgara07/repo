package tn.esprit.softib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.Reclamation;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.service.IReclamationService;

@RestController
@RequestMapping("/reclamation")

public class ReclamationController {
	@Autowired
	IReclamationService reclamationService;
	
	
	@GetMapping("/findAll")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public List<Reclamation> findAll() {
		List<Reclamation> reclamation = (List<Reclamation>) reclamationService.getAllReclamations();
		return reclamation;
	}
	
	
	@GetMapping("/findById/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Reclamation findById(@PathVariable("id") Long id) {
		Reclamation reclamation = reclamationService.getReclamationByID(id);
		return reclamation;
	}
	
	
	@PostMapping("/create")
	@ResponseBody
	public Reclamation save(@RequestBody Reclamation reclamation){
		Reclamation reclamationResult = reclamationService.addReclamation(reclamation);
		return reclamationResult;
	}
	
	
	@PutMapping("/update")
	@ResponseBody
	public Reclamation update(@RequestBody Reclamation reclamation){
		return reclamationService.updateReclamation(reclamation);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public void delete(@PathVariable("id") Long id){
		reclamationService.deleteReclamation(id);
	}
	
}
