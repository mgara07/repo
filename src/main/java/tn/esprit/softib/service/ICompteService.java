package tn.esprit.softib.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.softib.Response.ResponseMessage;
import tn.esprit.softib.entity.Compte;

public interface ICompteService {
	
	public List<Compte> getAllComptes();
	public Compte getCompteById(long id);
	public Compte addCompte(Compte compte);
	public ResponseEntity<ResponseMessage> deleteCompte(long id);
	public ResponseEntity<ResponseMessage> updateCompte(Compte compte);
	public Compte store(long id, MultipartFile file)  throws IOException;
	public ResponseEntity<ResponseMessage> verifCardType (long id);
	public List<Compte> getSatatistiqueParSalaire();
	

}
