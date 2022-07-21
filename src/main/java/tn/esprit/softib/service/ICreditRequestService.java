package tn.esprit.softib.service;

import java.util.List;
import java.util.Set;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.entity.CreditRequest;

public interface ICreditRequestService {
	public CreditRequest addCreditRequest(CreditRequest creditRequest, Integer id);
	 public String deleteCreditRequest(Integer id);
	 public String updateCreditRequest(Integer id, CreditRequest newCreditRequest) ;
	 public CreditRequest getCreditRequest(Integer id);
	 public String rejectCreditRequest(Integer id, CreditRequest creditrequest);
	 public String acceptCreditRequestChanges(Integer id);
	 public String createCreditFromCreditRequest(Integer id);
	 public Credit mapCreditFromCreditRequest(CreditRequest creditRequest);
	 public String treatCreditRequest(Integer id);
	 public CreditRequest checkEligibaleCreditRequest(CreditRequest creditRequest);
	 public Double calculateAmountToPayForSalary(CreditRequest creditRequest) ;
	 public List<CreditRequest> getAllCreditRequestAcceptedFromClients();
}
