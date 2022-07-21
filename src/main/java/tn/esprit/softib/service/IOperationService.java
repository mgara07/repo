package tn.esprit.softib.service;

import java.util.List;

import tn.esprit.softib.entity.FormByUserStat;
import tn.esprit.softib.entity.Operation;
import tn.esprit.softib.entity.OperationByStatus;
import tn.esprit.softib.enums.OperationStatus;



public interface IOperationService {

    Operation save(Operation operation);

    Operation findOperationById(Integer id);

    List<Operation> findOperationByAccount(long IdAccount);

    Operation updateOperationStatus(Integer idOperation, OperationStatus operationStatus);

    List<Operation> getArchivedOperation(boolean inArchived);

    List<Operation> getAllOperationByClient(long accountNumber);

    List<Operation> getAllOperationByClientAndStatus(long accountNumber, OperationStatus operationStatus);
	 List<OperationByStatus> getOperationFormsStats();

	List<Operation> getAllOperations();


}