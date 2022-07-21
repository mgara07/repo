package tn.esprit.softib.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.softib.entity.Compte;

public interface ICompteService {
	
	public List<Compte> getAllComptes();
	public Compte getCompteById(long id);
	public Compte addCompte(Compte compte);
	public void deleteCompte(long id);
	public Compte updateCompte(Compte compte);
	public Compte store(long id, MultipartFile file)  throws IOException;
	

}
