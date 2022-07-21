package tn.esprit.softib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.Reclamation;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.repository.ReclamationRepository;


@Service
public class ReclamationServiceImpl implements IReclamationService {
	
	@Autowired
	ReclamationRepository reclamationRepository;

	@Override
	public List<Reclamation> getAllReclamations() {
		return reclamationRepository.findAll();
	}

	@Override
	public Reclamation getReclamationByID(long id) {
		return reclamationRepository.findById(id).orElse(null);
	}

	@Override
	public Reclamation addReclamation(Reclamation newReclamation) {
		return reclamationRepository.save(newReclamation);
	}

	@Override
	public void deleteReclamation(long id) {
		reclamationRepository.deleteById(id);		
	}

	@Override
	@Transactional
	public Reclamation updateReclamation(Reclamation reclamation) {

			Reclamation oldReclamation = reclamationRepository.findById(reclamation.getUserId()).orElse(null);
			if (reclamation.getStatus()!=null 
					&& !("".equals(reclamation.getStatus()))
					&& !(oldReclamation.getStatus().equals(reclamation.getStatus()))) {
				oldReclamation.setStatus(reclamation.getStatus());
			}

			if (reclamation.getReclamationTitle()!=null 
					&& !("".equals(reclamation.getReclamationTitle().trim()))
					&& !(oldReclamation.getReclamationTitle().equals(reclamation.getReclamationTitle()))) {
				oldReclamation.setReclamationTitle(reclamation.getReclamationTitle());
			}
			if (reclamation.getReclamationDescription()!=null 
					&& !("".equals(reclamation.getReclamationDescription().trim()))
					&& !(oldReclamation.getReclamationDescription().equals(reclamation.getReclamationDescription()))) {
				oldReclamation.setReclamationDescription(reclamation.getReclamationDescription());
			}
			if (reclamation.getAssigneeName()!=null 
					&& !("".equals(reclamation.getAssigneeName().trim()))
					&& !(oldReclamation.getAssigneeName().equals(reclamation.getAssigneeName()))) {
				oldReclamation.setAssigneeName(reclamation.getAssigneeName());
			}
				
			reclamationRepository.save(oldReclamation);
			return oldReclamation;
		
	}

}
