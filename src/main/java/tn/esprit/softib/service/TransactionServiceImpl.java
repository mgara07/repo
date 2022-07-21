package tn.esprit.softib.service;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.Operation;
import tn.esprit.softib.entity.Transaction;
import tn.esprit.softib.enums.TypeTransaction;
import tn.esprit.softib.repository.TransactionRepository;
import tn.esprit.softib.validator.TransactionValidator;


@Slf4j
@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;
@Autowired
    IOperationService operationService;
@Autowired
    ICompteService accountService;

    @Override
    public Transaction save(Transaction transaction) {
        List<String> errors = TransactionValidator.validate(transaction);
        if (!errors.isEmpty()) {
            log.error("transaction is not valid {}", transaction);
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTransactionById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> getTransactionByType(TypeTransaction transactionType) {
        return transactionRepository.findAll().stream()
                .filter(x -> x.getTransactionType().equals(transactionType)).collect(Collectors.toList());
    }

//   @Override
//    public List<Transaction> getTransactionByOperation(long idOperation, Boolean isNegative) {
//        return transactionRepository.findAll().stream().filter(x -> x.getOperation().getId() == idOperation).collect(Collectors.toList());
//   }
//
//    @Override
//    public List<Transaction> getTransactionByOperation(long idOperation) {
//        return transactionRepository.findAll().stream().filter(x -> x.getOperation().getID()== idOperation).collect(Collectors.toList());
//    }
//
//    @Override
//    public BigDecimal getYearlyNegBalanceByClient(long idUser, int year) {
//
//        List<Operation> OpByUser = operationService.getAllOperationByClient(idUser)
//                .stream().filter(x -> x.getDate().getYear() == year).collect(Collectors.toList());
//        BigDecimal a = BigDecimal.ZERO;
//        OpByUser.stream().forEach(operation -> this.getTransactionByOperation(operation.getId(), true)
//                .stream().forEach((Consumer<? super Transaction>) a.add(operation.getAmount())));
//        return a;
//    }
//
//    @Override
//    public Integer countNegativeTransactionBalanceByAccount(long idAccount) {
//        int a = 0;
//       List <Operation> operations = operationService.findOperationByAccount(idAccount);
//       for(Operation o : operations){
//          List<Transaction> transactions = this.getTransactionByOperation(o.getId(),true);
//          a +=  transactions.size();
//       }
//       return a;
//    }


    @Override
    public BigDecimal getAllNegativeBalance() {
        BigDecimal a = BigDecimal.ZERO;
      List<Transaction> transactions = transactionRepository.findAll().stream().filter(x-> x.getIsNegativeTx()).collect(Collectors.toList());
      for(Transaction t : transactions){
          a.add(t.getOperation().getAmount());
      }
      return a;
    }

    @Override
    public List<Transaction> getMonthlyTransactions(Date date) {
          
    	List<Transaction>	a=	transactionRepository.findAll().stream().filter(x -> x.getDate().getMonthValue()==(date.getMonth()+1)).collect(Collectors.toList());
        		return a;
    }

    @Override
    public List<Transaction> getMonthlyTransactionsByClient(Integer id,Date date) {
        return null;
    }


    @Override
    public Transaction revertTransaction(Integer id) {
        checkIdTransaction(id);
        Transaction t = this.findTransactionById(id);
        t.setIsRevertedTransaction(true);
        return t;

    }


    private void checkIdTransaction(Integer idTxt) {
        if (idTxt == null) {
            log.error("Transaction ID is NULL");
        }
    }

	@Override
	public List<Transaction> getAllTransaction() {
		return transactionRepository.findAll();
	}

  

}
