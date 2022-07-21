package tn.esprit.softib.controller;


import java.math.BigDecimal;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.Transaction;
import tn.esprit.softib.enums.TypeTransaction;
import tn.esprit.softib.service.ITransactionService;




@RestController
public class TransactionController  {
    @Autowired
    ITransactionService transactionService;
    
    @PostMapping(value ="/transactions/create" )
    public ResponseEntity<Transaction> save(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.save(transaction));
    }

    @GetMapping(value ="/transactions/findbyid/{id}")
    public Transaction getTransactionById(@PathVariable("id") Integer id) {
        return transactionService.findTransactionById(id);
    }

    @GetMapping(value ="/transactions/findbytype/{type}")
    public List<Transaction> getTransactionBytype(@PathVariable("type") TypeTransaction transactionType) {
        return transactionService.getTransactionByType(transactionType);
    }

//    @GetMapping(value = "/transactions/internal/negative")
//    public BigDecimal getAllNegativeBalance() {
//        return transactionService.getAllNegativeBalance();
//    }

    @GetMapping(value = "/transactions/")
    public List<Transaction> getMonthlyTransactions(@RequestParam("date") Date date) {
        return transactionService.getMonthlyTransactions(date);
    }

    
    @GetMapping("/transactions/findAll")
	@ResponseBody
	public List<Transaction> findAll(){
		List<Transaction> transactions = (List<Transaction>) transactionService.getAllTransaction();
		return transactions;
	}
    
}
