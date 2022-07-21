package tn.esprit.softib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.softib.entity.CardRequest;


@Repository
public interface CardRequestRepository extends JpaRepository<CardRequest, Long>{
	
	Optional<CardRequest> findByRib(String rib);
	

}
