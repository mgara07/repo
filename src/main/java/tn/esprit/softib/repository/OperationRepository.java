package tn.esprit.softib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.softib.entity.Operation;
import tn.esprit.softib.enums.FormStatus;
import tn.esprit.softib.enums.OperationStatus;


@Repository
public interface OperationRepository extends JpaRepository<Operation,Integer> {
	
	@Query("SELECT COUNT(*) FROM Operation o WHERE o.id=:id AND o.operationStatus=:status")
	public  int findoperationByStatus(@Param("id")int  id,
			@Param("status")OperationStatus status);
}