package tn.esprit.softib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.softib.entity.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
	 Optional<Compte> findByStatus(boolean status);
	    List<Compte> findByEmailsentAndStatus(boolean emailSent, boolean status);
	    long countByStatus(boolean status);
	    long countByEmailsent(boolean emailSent);
	    
	
}
