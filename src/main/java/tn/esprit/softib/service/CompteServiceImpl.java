package tn.esprit.softib.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.softib.Response.ResponseMessage;
import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.enums.Nature;
import tn.esprit.softib.repository.CompteRepository;
import tn.esprit.softib.repository.UserRepository;

@Service
public class CompteServiceImpl implements ICompteService {
	@Autowired
	CompteRepository compteRepository;
	@Autowired
	UserRepository userRepository;

	
	@Override
	public List<Compte> getAllComptes() {
		return compteRepository.findAll();
	}


	@Override
	public Compte getCompteById(long id) {
		// TODO Auto-generated method stub
		return compteRepository.findById(id).orElse(null);
		
	}

	@Override
	public Compte addCompte(Compte newCompte) {
		// TODO Auto-generated method stub
		return compteRepository.save(newCompte);
	}


	@Override
	public ResponseEntity<ResponseMessage> deleteCompte(long id) {
		try {
		 compteRepository.deleteById(id);
		 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Element deleted sucessfully !"));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error deleting element ! "));
			
		}

	}

	@Override
	public ResponseEntity<ResponseMessage> updateCompte(Compte compte) {
		// TODO Auto-generated method stub
		Compte oldCompte = compteRepository.findById(compte.getId()).orElse(null);
		if (null != compte.getNomComplet() 
				&& !("".equals(compte.getNomComplet()))
				&& !(oldCompte.getNomComplet().equals(compte.getNomComplet()))) {
			oldCompte.setNomComplet(compte.getNomComplet());
		} else {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Please Fill the value of nomComplet"));
		}
		if (null != compte.getNatureCompte()
				&& oldCompte.getNatureCompte() != compte.getNatureCompte()) {
			oldCompte.setNatureCompte(compte.getNatureCompte());
		} else {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Please Fill the value of NatureCompte"));
		}
		if (null != compte.getRib()
				&& !("".equals(compte.getRib().trim()))
				&& !(oldCompte.getRib().equals(compte.getRib()))) {
			oldCompte.setRib(compte.getRib());
		}
		if (null  != compte.getIban()
				&& !("".equals(compte.getIban().trim()))
				&& !(oldCompte.getIban().equals(compte.getIban()))) {
			oldCompte.setIban(compte.getIban());
		}
		if (null != compte.getCodeBic() 
				&& !("".equals(compte.getIban().trim()))
				&& !(oldCompte.getCodeBic().equals(compte.getCodeBic()))) {
			oldCompte.setCodeBic(compte.getCodeBic());
		}
		if (new BigDecimal(0)!=  compte.getSolde()
				&& oldCompte.getSolde() != compte.getSolde()) {
			oldCompte.setSolde(compte.getSolde());
		}
		compte.setUser(oldCompte.getUser());
		try {
		compteRepository.save(compte);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("You have a constraint violation please check !"));
			
		}

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Element updated sucessfully !"));
	}
	 public Compte store(long id, MultipartFile file) throws IOException {
		    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		    Compte newCompte = getCompteById(id);
		    newCompte.setData(file.getBytes());
		    return compteRepository.save(newCompte);
		  }


	@Override
	public ResponseEntity<ResponseMessage> verifCardType(long id) {
		// TODO Auto-generated method stub
		Compte compte = compteRepository.getById(id);
		User user = userRepository.getById(compte.getUser().getId());
		float salaire = user.getSalaireNet();
		if(salaire < 1000) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("You can get a PINK CARD "
					+ "with bank limit "+ salaire * 0.2 +" per week"));
		} else if(salaire < 2500) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("You can get a GREEN CARD "
					+ "with bank limit "+ salaire * 0.2 +" per week"));
		} else if(salaire < 4500) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("You can get a RED CARD "
					+ "with bank limit "+ salaire * 0.2 +" per week"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("You can get a GOLDEN CARD "
					+ "with bank limit "+ salaire * 0.2 +" per week"));
		}
		
	}


	@Override
	public List<Compte> getSatatistiqueParSalaire() {
		// TODO Auto-generated method stub
		return compteRepository.getSatatistiqueParSalaire();
	}
	
	


	

	 
	 
	



}
