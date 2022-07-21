package tn.esprit.softib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.enums.FormStatus;

@Repository
public interface FormulaireRepository extends JpaRepository<Formulaire, Long> {

	Optional<Formulaire> findByEmail(String email);
	
	@Query("SELECT COUNT(f) FROM Formulaire f WHERE f.cin=:cin AND f.formStatus=:status")
	public int findFormsByStatus(@Param("cin")String cin,
			@Param("status")FormStatus status);
}
