package tn.esprit.softib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.softib.entity.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
	 Optional<Compte> findByStatus(boolean status);
	    List<Compte> findByEmailsentAndStatus(boolean emailSent, boolean status);
	    long countByStatus(boolean status);
	    long countByEmailsent(boolean emailSent);
	    //@Query("SELECT c FROM Compte c INNER JOIN c.id WHERE c.user.user_id= :userId AND c.user.salaire > 6000")
	  //  @Query("SELECT c FROM Compte c WHERE c.id= :userId")
	   // @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name LIKE '%:roleName%'")
	    //@Query("SELECT c FROM Compte c JOIN c.user u WHERE u.id = :userId AND u.salaireNet > 6000")
	    @Query("SELECT c FROM Compte c JOIN c.user u WHERE u.salaireNet > 6000")
		List<Compte> getSatatistiqueParSalaire();
	    
	
}
