package tn.esprit.softib.service;

import java.util.List;
import java.util.Set;

import tn.esprit.softib.entity.ConfirmationMessage;
import tn.esprit.softib.entity.FormByUserStat;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.User;

public interface IFormulaireService {

	public List<Formulaire> getAllFormulaires();
	public Formulaire getFormulaireById(long id);
	public Formulaire getFormulaireByEmail(String email);
	public Formulaire addFormulaire(Formulaire formulaire);
	public void deleteFormulaire(long id);
	public Formulaire updateFormulaire(Formulaire formulaire);
	public ConfirmationMessage confirmFormulaire(long id);
	public Formulaire rejectFormulaire(long id);
	public List<FormByUserStat> getUserFormsStats();
	
}
