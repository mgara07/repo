package tn.esprit.softib.service;

import java.util.List;

import tn.esprit.softib.entity.Reclamation;

public interface IReclamationService {
	public List<Reclamation> getAllReclamations();
	public Reclamation getReclamationByID(long id);
	public Reclamation addReclamation(Reclamation newReclamation);
	public void deleteReclamation(long id);
	public Reclamation updateReclamation(Reclamation reclamation);
}
