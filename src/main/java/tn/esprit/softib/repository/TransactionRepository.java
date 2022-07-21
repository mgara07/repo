package tn.esprit.softib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.softib.entity.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
