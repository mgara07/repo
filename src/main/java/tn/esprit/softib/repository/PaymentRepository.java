package tn.esprit.softib.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.entity.Payment;
import tn.esprit.softib.enums.CreditStatus;

public interface PaymentRepository extends CrudRepository<Payment, Long> {


    @Query("SELECT p FROM Payment p WHERE p.credit = ?1 and p.paymentStatus = ?2 ORDER BY p.paymentDueDate")
    Set<Payment> findAllNotPayedPayments(Credit creditId, CreditStatus status);
}
