package tn.esprit.softib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.entity.CreditRequest;
import tn.esprit.softib.entity.Payment;
import tn.esprit.softib.enums.CreditStatus;
import tn.esprit.softib.repository.CreditRepository;
import tn.esprit.softib.repository.PaymentRepository;
import tn.esprit.softib.utility.SystemDeclarations;

@Service
public class CreditServiceImpl implements ICreditService {

	@Autowired
    private CreditRepository creditRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Override
    public Credit addPayment(Payment payment, Integer creditId)  {
    	Credit credit = creditRepository.findById(creditId.longValue()).get();
        Set<Payment> payments = credit.getPayments();       
        payments.add(payment);
        credit.setPayments(payments);
        
        payment.setCreationDate(LocalDate.now());
        paymentRepository.save(payment);
        creditRepository.save(credit);
        return credit;
    }
    
    
    @Override
    public Credit addCredit(Credit credit) {
    	
    	if (credit.getCreditStatus() != null) {
            credit.setCreditStatus(credit.getCreditStatus()); 
            }
            
    	if (credit.getCreditAmount() != null) {
           
            	credit.setCreditAmount(credit.getCreditAmount());
            
            if (credit.getCreditRequest().getInsurance() != null) {
            	if (credit.getCreditRequest().getInsurance().getAmount() != null) {
            Set<Payment> payments = new HashSet<>();
            Double totalCreditAmount = credit.getCreditAmount() + (credit.getCreditAmount() * SystemDeclarations.CREDIT_INTEREST) + SystemDeclarations.CREDIT_FEES + credit.getCreditRequest().getInsurance().getAmount();
                    Payment payment = null;
            LocalDate date = LocalDate.now();
            LocalDate firstDayOfMonth = LocalDate.of(date.getYear(),date.getMonth(),1);
            if (credit.getCreditTerm()!= null) {
            for(int i = 0; i< credit.getCreditTerm(); i++){
                firstDayOfMonth = firstDayOfMonth.plusMonths(1);
                payment = new Payment();
                payment.setCreationDate(date);
                payment.setPaymentStatus(CreditStatus.CREATED);
                payment.setPaymentAmount(totalCreditAmount / credit.getCreditTerm());
                payment.setPaymentInterest(credit.getCreditInterest());
                payment.setPaymentDueDate(firstDayOfMonth);
                payment.setPaymentInterest(SystemDeclarations.CREDIT_INTEREST);
                payments.add(payment);
            }
            credit.setPayments(payments);
            credit.setPayedAmount(0d);
            credit.setRemainingAmount(totalCreditAmount);
            creditRepository.save(credit);
            for (Payment payment1 : payments){
                payment1.setCredit(credit);
            }
            paymentRepository.saveAll(payments);
            } 
            } 
            }
    	}
            if (credit.getCreditTerm()!= null) {
            credit.setCreditTerm(credit.getCreditTerm());
            }
            if (credit.getCreditRepayment() != null) {
            credit.setCreditRepayment(credit.getCreditRepayment());
            }
            if (credit.getCreditRepaymentAmount() != null) {
            credit.setCreditRepaymentAmount(credit.getCreditRepaymentAmount());
            }
            if (credit.getType() != null) {
            credit.setType(credit.getType());
            }

            if (credit != null) {
            credit.setCreditRequest(credit.getCreditRequest());
            }
            if (credit.getCompte() != null) {
            credit.setCompte(credit.getCompte());
            }
            if (credit.getCreationDate() != null) {
            credit.setCreationDate(credit.getCreationDate());
            }
            if (credit.getCreditInterest() != null) {
            credit.setCreditInterest(credit.getCreditInterest());
            }
            if (credit.getCreditRepaymentInterest() != null) {
            credit.setCreditRepaymentInterest(credit.getCreditRepaymentInterest());
            }
            if (credit.getCreditFees() != null) {
            credit.setCreditFees(credit.getCreditFees());
            }
            if (credit.getAgent() != null) {
            credit.setAgent(credit.getAgent());
            }
            if (credit.getReleaseDate() != null) {
            credit.setReleaseDate(credit.getReleaseDate());
            } 
            creditRepository.save(credit);
            return credit ;
    }
    
    
    
 

    @Override
    public String deleteCredit(Integer id)  {
        if (creditRepository.findById(id.longValue()).isPresent()) {
            creditRepository.deleteById(id.longValue());
            return "Credit Deleted Successfully";
        } else
            return "Credit Not Found";
    }

    @Override
    public String updateCredit(Integer id, Credit newCredit)  {
        if (creditRepository.findById(id.longValue()).isPresent()) {
            Credit oldCredit = creditRepository.findById(id.longValue()).get();
            if (newCredit.getAgent() != null) {
                oldCredit.setAgent(newCredit.getAgent());
            }
            if (newCredit.getCreditTerm() != null) {
                oldCredit.setCreditTerm(newCredit.getCreditTerm());
            }
            if (newCredit.getCreditAmount() != null) {
                oldCredit.setCreditAmount(newCredit.getCreditAmount());
            }
            if (newCredit.getCreditRepayment() != null) {
                oldCredit.setCreditRepayment(newCredit.getCreditRepayment());
            }
            if (newCredit.getCreditRepaymentAmount() != null) {
                oldCredit.setCreditRepaymentAmount(newCredit.getCreditRepaymentAmount());
            }
            if (newCredit.getCreditFees() != null) {
                oldCredit.setCreditFees(newCredit.getCreditFees());
            }
            if (newCredit.getCreditInterest() != null) {
                oldCredit.setCreditInterest(newCredit.getCreditInterest());
            }
            if (newCredit.getCreditRepaymentInterest() != null) {
                oldCredit.setCreditRepaymentInterest(newCredit.getCreditRepaymentInterest());
            }
            if (newCredit.getPayedAmount() != null) {
                oldCredit.setPayedAmount(newCredit.getPayedAmount());
            }
            if (newCredit.getRemainingAmount() != null) {
                oldCredit.setRemainingAmount(newCredit.getRemainingAmount());
            }
            if (newCredit.getReleaseDate() != null) {
                oldCredit.setReleaseDate(newCredit.getReleaseDate());
            }
            if (newCredit.getType() != null) {
                oldCredit.setType(newCredit.getType());
            }
            creditRepository.save(oldCredit);
            return "Credit Updated Successfully";
        } else {
            return "Credit Not Found";
        }
    }

    @Override
    public Credit getCredit(Integer id)  {
        return creditRepository.findById(id.longValue()).get();
    }
	
}
