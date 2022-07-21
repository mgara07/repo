package tn.esprit.softib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.enums.ERole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByCin(String cin);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);

	Boolean existsByCin(String cin);

	Boolean existsByEmail(String email);
	
	Boolean existsByUsername(String username);
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name LIKE '%:roleName%'")
	List<User> findClients(ERole roleName);
	
	 @Query(nativeQuery=true, value = "DELETE FROM User u WHERE DATE(u.creation_Date) < DATE(NOW() - INTERVAL 7 DAY)")
	 public Compte deleteAutoUser();
}
