package tn.esprit.softib.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.softib.entity.Reclamation;
import tn.esprit.softib.enums.ReclamationStatus;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long>{
	  List<Reclamation> findAllByStatus(ReclamationStatus reclamationStatus);
}
