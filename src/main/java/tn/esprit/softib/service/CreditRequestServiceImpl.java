package tn.esprit.softib.service;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.enums.CreditStatus;
import tn.esprit.softib.enums.TypeCredit;
import tn.esprit.softib.entity.CreditRequest;
import tn.esprit.softib.entity.Insurance;
import tn.esprit.softib.repository.CreditRepository;
import tn.esprit.softib.repository.CreditRequestRepository;
import tn.esprit.softib.repository.InsuranceRepository;
import tn.esprit.softib.utility.SystemDeclarations;

@Service
public class CreditRequestServiceImpl implements ICreditRequestService {
	
	@Autowired
    private CreditRequestRepository creditRequestRepository;
	
	@Autowired
	private CreditRepository creditRepository;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private CreditServiceImpl creditService;

    @Autowired
    private CompteServiceImpl CompteService;
  

    
    @Override
    public CreditRequest addCreditRequest(CreditRequest creditRequest, Integer id)  {
        creditRequest.setCreationDate(new Date());
        creditRequest.setCreditRequestStatus(CreditStatus.CREATED);
        creditRequest.setCompte(CompteService.getCompteById(id.longValue()));
        return creditRequestRepository.save(creditRequest);
    }

    @Override
    public String deleteCreditRequest(Integer id)  {
        if (creditRequestRepository.findById(id.longValue()).isPresent()) {
            creditRequestRepository.deleteById(id.longValue());
            return "Credit Request Deleted Successfully";
        } else
            return "Credit Request Not Found";
    }

    @Override
    public String updateCreditRequest(Integer id, CreditRequest newCreditRequest)  {
        if (creditRequestRepository.findById(id.longValue()).isPresent()) {
            CreditRequest oldCreditRequest = creditRequestRepository.findById(id.longValue()).get();
            if (newCreditRequest.getCin() != null) {
                oldCreditRequest.setCin(newCreditRequest.getCin());
            }
            if (newCreditRequest.getCreditTerm() != null) {
                oldCreditRequest.setCreditTerm(newCreditRequest.getCreditTerm());
            }
            if (newCreditRequest.getCreditAmount() != null) {
                oldCreditRequest.setCreditAmount(newCreditRequest.getCreditAmount());
            }
            if (newCreditRequest.getCreditRepayment() != null) {
                oldCreditRequest.setCreditRepayment(newCreditRequest.getCreditRepayment());
            }
            if (newCreditRequest.getCreditRepaymentAmount() != null) {
                oldCreditRequest.setCreditRepaymentAmount(newCreditRequest.getCreditRepaymentAmount());
            }
            if (newCreditRequest.getAddress() != null) {
                oldCreditRequest.setAddress(newCreditRequest.getAddress());
            }
            if (newCreditRequest.getCivilState() != null) {
                oldCreditRequest.setCivilState(newCreditRequest.getCivilState());
            }
            if (newCreditRequest.getJob() != null) {
                oldCreditRequest.setJob(newCreditRequest.getJob());
            }
            if (newCreditRequest.getAge() != null) {
                oldCreditRequest.setAge(newCreditRequest.getAge());
            }
            if (newCreditRequest.getCreditRequestStatus() != null) {
                oldCreditRequest.setCreditRequestStatus(newCreditRequest.getCreditRequestStatus());
            }
            creditRequestRepository.save(oldCreditRequest);
            return "Credit Request Updated Successfully";
        } else {
            return "Credit Request Not Found";
        }
    }

    @Override
    public CreditRequest getCreditRequest(Integer id) {
    	if (creditRequestRepository.findById(id.longValue()).isPresent()) {
    	return creditRequestRepository.findById(id.longValue()).get(); }
    	else return null ;
    }

    @Override
    public String rejectCreditRequest(Integer id, CreditRequest newCreditRequest) {
    	if (creditRequestRepository.findById(id.longValue()).isPresent()) {
            CreditRequest oldCreditRequest = creditRequestRepository.findById(id.longValue()).get();
            if (oldCreditRequest.getCreditRequestStatus().equals(CreditStatus.CREATED)) {
            	if (newCreditRequest.getRejectionReason() != null) {
                oldCreditRequest.setRejectionReason(newCreditRequest.getRejectionReason());
            } else return "please note rejection reasons";
            oldCreditRequest.setCreditRequestStatus(CreditStatus.REJECTED);
            creditRequestRepository.save(oldCreditRequest);
            return "Credit Request Reject"; 
            } 
            else {
            	return "please check credit request status (should be CREATED)";
            }
             
        } else {
            return "Credit Request Not Found";
        }
    }


    @Override
    public String acceptCreditRequestChanges(Integer id)  {
    	if (creditRequestRepository.findById(id.longValue()).isPresent()) {
    		CreditRequest creditRequest = creditRequestRepository.findById(id.longValue()).get();
            if (creditRequest.getCreditRequestStatus().toString().equals(CreditStatus.WAITINGFORCLIENTACCEPTANCE.toString())) {
                creditRequest.setCreditRequestStatus(CreditStatus.ACCEPTED);
                creditRequest.setRejectionReason("None");
                creditRequestRepository.save(creditRequest);
                return "Client Accepted changes susccessfully" ;
            } else return "please check credit request status (should be WAITING FOR CLIENT ACCEPTANCE";
        }
    	return "Credit Request Not Found";
    }

    @Override
    public String createCreditFromCreditRequest(Integer id)  {
    	if (creditRequestRepository.findById(id.longValue()).isPresent()) {
    		CreditRequest creditRequest = creditRequestRepository.findById(id.longValue()).get();
            if (creditRequest.getCreditRequestStatus().equals(CreditStatus.VALIDATED)) {
                creditRequest.setCreditRequestStatus(CreditStatus.CONFIRMED);
                creditRequestRepository.save(creditRequest);
                creditService.addCredit(mapCreditFromCreditRequest(creditRequest));
                return "Credit CONFIRMED Successfully";
            } else return "Credit Request status should be VALIDATED to confirm a credit";
    	} else return "Credit Request Not Found";    	
    }

    @Override
    public Credit mapCreditFromCreditRequest(CreditRequest creditRequest) {
        Credit credit = new Credit();
        if (creditRequest.getCreditRequestStatus() != null) {
        credit.setCreditStatus(creditRequest.getCreditRequestStatus()); 
        }
        if (creditRequest.getCreditAmount() != null) {
        credit.setCreditAmount(creditRequest.getCreditAmount());
        }
        if (creditRequest.getCreditTerm()!= null) {
        credit.setCreditTerm(creditRequest.getCreditTerm());
        }
        if (creditRequest.getCreditRepayment() != null) {
        credit.setCreditRepayment(creditRequest.getCreditRepayment());
        }
        if (creditRequest.getCreditRepaymentAmount() != null) {
        credit.setCreditRepaymentAmount(creditRequest.getCreditRepaymentAmount());
        }
        if (creditRequest.getType() != null) {
        credit.setType(creditRequest.getType());
        }

        if (creditRequest != null) {
        credit.setCreditRequest(creditRequest);
        }
       
        if (creditRequest.getCompte() != null) {
        credit.setCompte(creditRequest.getCompte());
        }
        
        credit.setCreationDate(LocalDate.now());
        credit.setCreditInterest(SystemDeclarations.CREDIT_INTEREST);
        credit.setCreditRepaymentInterest(SystemDeclarations.CREDIT_INTEREST);
        credit.setCreditFees(SystemDeclarations.CREDIT_FEES);
        credit.setAgent("BankAgent");
        credit.setReleaseDate(credit.getCreationDate().plusDays(15));
        creditRepository.save(credit);
        
        return credit;
    }

    @Override
    public String treatCreditRequest(Integer id) {
        if (creditRequestRepository.findById(id.longValue()).isPresent()) {
            CreditRequest creditRequest = creditRequestRepository.findById(id.longValue()).get();
            creditRequest = checkEligibaleCreditRequest(creditRequest);
            creditRequestRepository.save(creditRequest);
            return ("Your credit has been "+ creditRequest.getCreditRequestStatus()+ "\n" + "   Rejection Reaseon : " + creditRequest.getRejectionReason());

        }
        return "Credit Request Not Found";
    }


    @Override
    public CreditRequest checkEligibaleCreditRequest(CreditRequest creditRequest) {
        StringBuilder rejectionReason = new StringBuilder();
        boolean check = true;
        
        
        if (creditRequest.getInsurance() == null) {
            rejectionReason.append(SystemDeclarations.CREDIT_INSURANCE_NULL);
            check = false;
        }
        
        if (creditRequest.getCreditTerm() != null) {
        if (calculateAmountToPayForSalary(creditRequest) >= 0.4) {
            if (rejectionReason.length() > 0) {
                rejectionReason.append(", ");
            }
            rejectionReason.append(SystemDeclarations.CREDIT_NOT_ENOUGH_SALARY);
            check = false;
        }
    }
        if (creditRequest.getType()!= null) {
        if (creditRequest.getType().toString().equals(TypeCredit.CONSUMPTION.toString())) {
            if (creditRequest.getCreditAmount() > 20000) {
                check = false;
                if (rejectionReason.length() > 0) {
                    rejectionReason.append(", ");
                }
                rejectionReason.append(SystemDeclarations.CREDIT_CONSUMPTION_AMOUNT_EXCEEDED);
            }
            if (creditRequest.getCreditTerm() > 36) {
                check = false;
                if (rejectionReason.length() > 0) {
                    rejectionReason.append(", ");
                }
                rejectionReason.append(SystemDeclarations.CREDIT_CONSUMPTION_CREDIT_TERM_EXCEEDED);
            }
        }
        
        
        
        if (creditRequest.getType().toString().equals(TypeCredit.CAR.toString())) {
            if (creditRequest.getCreditTerm() > 87) {
                check = false;
                if (rejectionReason.length() > 0) {
                    rejectionReason.append(", ");
                }
                rejectionReason.append(SystemDeclarations.CREDIT_CAR_CREDIT_TERM_EXCEEDED);
            }
        }
        
        
        if (creditRequest.getType().toString().equals(TypeCredit.HOME.toString())) {
            if (creditRequest.getCreditTerm() > 300) {
                check = false;
                if (rejectionReason.length() > 0) {
                    rejectionReason.append(", ");
                }
                rejectionReason.append(SystemDeclarations.CREDIT_HOME_CREDIT_TERM_EXCEEDED);
            }
        }
        
        }
        
        
        if (check) {
            creditRequest.setCreditRequestStatus(CreditStatus.VALIDATED);
            creditRequest.setRejectionReason(null);
        } else {
            creditRequest.setCreditRequestStatus(CreditStatus.REJECTED);
            creditRequest.setRejectionReason(rejectionReason.toString());
        }
        return creditRequest;
    }
    
    
    @Override
    public Double calculateAmountToPayForSalary(CreditRequest creditRequest) {
        Integer creditTerm = creditRequest.getCreditTerm();
        Double creditAmount = 0d;
        if(creditRequest.getInsurance() != null) {
            creditAmount = creditRequest.getCreditAmount() + (creditRequest.getCreditAmount() * SystemDeclarations.CREDIT_INTEREST) + creditRequest.getInsurance().getAmount() + SystemDeclarations.CREDIT_FEES;
        } else {
            creditAmount = creditRequest.getCreditAmount() + (creditRequest.getCreditAmount() * SystemDeclarations.CREDIT_INTEREST)+ SystemDeclarations.CREDIT_FEES;

        }
        if(creditRequest.getNetSalary() != null) {
        Double netSalary = creditRequest.getNetSalary(); 
        Double amountToPay = creditAmount / creditTerm;
        return amountToPay / netSalary; }
        else return null;
    }

    
    @Override
    public List<CreditRequest> getAllCreditRequestAcceptedFromClients(){
    	List<CreditRequest> credits = creditRequestRepository.findAllCreditRequestWithStatus(CreditStatus.ACCEPTED);
        return credits ;
    }

}
