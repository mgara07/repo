package tn.esprit.softib.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.softib.entity.FormByUserStat;
import tn.esprit.softib.entity.Operation;
import tn.esprit.softib.entity.OperationByStatus;
import tn.esprit.softib.entity.Transaction;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.enums.ERole;
import tn.esprit.softib.enums.FormStatus;
import tn.esprit.softib.enums.OperationStatus;
import tn.esprit.softib.enums.OperationSubType;
import tn.esprit.softib.enums.OperationType;
import tn.esprit.softib.enums.TypeTransaction;
import tn.esprit.softib.entity.Operation;
import tn.esprit.softib.entity.Transaction;
import tn.esprit.softib.repository.OperationRepository;
import tn.esprit.softib.service.IOperationService;
import tn.esprit.softib.service.ITransactionService;
import tn.esprit.softib.validator.OperationValidator;
@Service
@Slf4j
public class OperationServiceImpl implements IOperationService {
    @Autowired
    OperationRepository operationRepository;
    ITransactionService transactionService;
	IOperationService operationService;

    @Override
    public Operation save(Operation operation) {
        List<String> errors = OperationValidator.validate(operation);
        if (!errors.isEmpty()) {
            log.error("Operation is not valid {}", operation);
        }
        operation.setOperationStatus(OperationStatus.TO_BE_EXECUTED);
      //  operation.setOperationtype(OperationType.PAYMENT);
		//operation.getCompte().setSolde(new BigDecimal(40000));
        Collection<Transaction> transactions = new ArrayList<>();
        if(operation.getOperationtype().equals(OperationType.PAYMENT) || operation.getOperationtype().equals(OperationType.RETRIEVE)){
           if(operation.getCompte().getSolde().compareTo(operation.getAmount())>0){

              Transaction T1 = new Transaction(operation.getDate(), TypeTransaction.CREDIT,false,operation, operation.getCompte().getSolde());
                Transaction T2 = new Transaction(operation.getDate(), TypeTransaction.CREDIT,false,operation,
                        operation.getAmount().subtract(operation.getCompte().getSolde())
                        );
                transactions.add(T1);
                transactions.add(T2);
                operation.getCompte().setSolde(operation.getAmount().subtract(operation.getCompte().getSolde()));
//                if(operation.getCompte() instanceof CurrentAccount){
//                    ((CurrentAccount) operation.getAccount()).setRecoveredAmount(((CurrentAccount) operation.getAccount()).getRecoveredAmount().subtract(T2.getMovement()));
//                }

          //     operation.addTransactions(transactions);
        }else{
                transactions.add(new Transaction(operation.getDate(), TypeTransaction.CREDIT,false,operation,operation.getAmount()));
                operation.setTransactions(transactions);
            }
        } 
      //v  operation.setTransactions(transactions);
        operation.setOperationStatus(OperationStatus.EXECUTED);
        return operationRepository.save(operation);
    }

    @Override
    public Operation findOperationById(Integer id) {
        return operationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Operation> findOperationByAccount(long idAccount) {
        return operationRepository.findAll().stream().filter(x->idAccount==x.getCompte().getId()).collect(Collectors.toList());
    }

    @Override
    public Operation updateOperationStatus(Integer IdOperation, OperationStatus operationStatus) {
        checkIdOperation(IdOperation);
        checkStatus(operationStatus);
        if (operationStatus.equals(OperationStatus.EXECUTED) || operationStatus.equals(OperationStatus.CANCELLED)) {
            Operation o = operationRepository.getById(IdOperation);
            o.setOperationStatus(operationStatus);
            return o;
        }
        return null;

    }

    @Override
    public List<Operation> getArchivedOperation(boolean isArchived) {
        return operationRepository.findAll().stream().filter(x -> x.getIsArchived().equals(isArchived)).collect(Collectors.toList());
    }



    @Override
    public List<Operation> getAllOperationByClient(long accountNumber) {
        return operationRepository.findAll().stream()
                .filter(x -> x.getCompte().getId() == accountNumber)
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> getAllOperationByClientAndStatus(long accountNumber, OperationStatus operationStatus) {
        Collection<Operation> operations = getAllOperationByClient(accountNumber);
        return operations.stream().filter(x -> x.getOperationStatus().equals(operationStatus)).collect(Collectors.toList());
    }

//    @Override
//    public Operation revertOperation(Integer idOperation) {
//        Operation operation = this.findOperationById(idOperation);
//       operation.setOperationStatus(OperationStatus.CANCELLED);
//       if(operation.getAccount() instanceof CurrentAccount){
//           manageRevertForCurrentAccount(operation);
//       }else{
//           manageRevertForSavingAccount(operation);
//       }
//        return operation;
//    }

//    private void manageRevertForSavingAccount(Operation operation) {
//        List<Transaction> transactions = transactionService.getTransactionByOperation(operation.getId(),false);
//        for (Transaction t : transactions){
//            if(t.getTransactionType().equals(TypeTransaction.CREDIT)) {
//                operation.getAccount().setBalance(operation.getAccount().getBalance().add(operation.getAmount()));
//            }else{
//                operation.getAccount().setBalance(operation.getAccount().getBalance().add(operation.getAmount().negate()));
//            }
//            transactionService.revertTransaction(t.getId());
//        }
//    }



    private void checkIdOperation(Integer idopt) {
        if (idopt == null) {
            log.error("Operation ID is NULL");
           
        }
    }

    private void checkStatus(OperationStatus operationStatus) {
        if (!StringUtils.hasLength(String.valueOf(operationStatus))) {
            log.error("Operation status is NULL");
          //  throw new InvalidOperationException("IS not allowed to chagne status to null",
          //          ErrorCodes.CREDIT_NON_MODIFIABLE);
        }

    }

    public void processCreditBill(Map<Integer,BigDecimal> map ){
        LocalDate calendar =  LocalDate.now();
        map.forEach((k,v) ->this.save(new Operation(calendar.plusMonths(k),
                v,true,OperationType.RETRIEVE, OperationSubType.Regluement_Credit,OperationStatus.TO_BE_EXECUTED))
        );
    }
    @Override
	public List<OperationByStatus> getOperationFormsStats() {
    	List<OperationByStatus> stats = new ArrayList<OperationByStatus>();
		List<Operation> operations = new ArrayList<>();
		for (Operation op : getAllOperations()) {
			
			operations.add(op);
			}
		
		for (Operation op : operations) {
			OperationByStatus stat = new OperationByStatus();
			stat.setId(op.getId());
			int executed = operationRepository.findoperationByStatus(op.getId(), OperationStatus.EXECUTED);
			stat.setExecuted(executed);
			int to_be_executed = operationRepository.findoperationByStatus(op.getId(), OperationStatus.TO_BE_EXECUTED);
			stat.setTo_be_executed(to_be_executed);
			int cancelled = operationRepository.findoperationByStatus(op.getId(), OperationStatus.CANCELLED);
			stat.setCancelled(cancelled);
			stats.add(stat);

		}	
		return stats;
}
    
	@Override
	public List<Operation> getAllOperations() {
		return operationRepository.findAll();
	}
}
