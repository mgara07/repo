package tn.esprit.softib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.softib.entity.CreditRequest;
import tn.esprit.softib.enums.CreditStatus;

public interface CreditRequestRepository extends CrudRepository<CreditRequest, Long> {

    @Query("SELECT p FROM CreditRequest p WHERE p.creditRequestStatus = ?1 ORDER BY p.creationDate DESC ")
    List<CreditRequest> findAllCreditRequestWithStatus(CreditStatus status);
}