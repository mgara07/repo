package tn.esprit.softib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import tn.esprit.softib.entity.CreditRequest;
import tn.esprit.softib.entity.Insurance;
import tn.esprit.softib.repository.CreditRequestRepository;
import tn.esprit.softib.repository.InsuranceRepository;
import tn.esprit.softib.utility.SystemDeclarations;

import java.text.MessageFormat;
import java.util.Date;

@Service
@Slf4j
public class InsuranceServiceImpl implements IInsuranceService {
	
	
	@Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CreditRequestRepository creditRequestRepository;

    @Override
    public String addInsurance(Integer creditRequestId) {
        if (creditRequestRepository.findById(creditRequestId.longValue()).isPresent()) {
            CreditRequest creditRequest = creditRequestRepository.findById(creditRequestId.longValue()).get();
            Insurance insurance = new Insurance();
            insurance.setType(creditRequest.getType());
            insurance.setCreationDate(new Date());
            insurance.setAmount(creditRequest.getCreditAmount()* SystemDeclarations.INSURANCE_PERCENTAGE);
            insurance.setBeneficiary("Bank Agent");
            creditRequest.setInsurance(insurance);
            insuranceRepository.save(insurance);
            creditRequestRepository.save(creditRequest);
            log.info("Insurance With Amount "+ insurance.getAmount() +" Added To Credit Request Successfully");
            return "Insurance With Amount "+ insurance.getAmount() +" Added To Credit Request Successfully";
            
        }
        log.error("Insurance Cannot Be Added : Credit Request Not Found");
        return "Insurance Cannot Be Added : Credit Request Not Found";
        
    }

    @Override
    public String deleteInsurance(Integer id)  {
    	
    if(insuranceRepository.findById(id.longValue()).isPresent()){
    	Insurance InsuranceDelete = insuranceRepository.findById(id.longValue()).get();
    
    	if (InsuranceDelete.getCreditRequest()!= null) {
        CreditRequest creditRequest = InsuranceDelete.getCreditRequest();
        creditRequest.setInsurance(null);
        creditRequestRepository.save(creditRequest);
        log.info("Delete Insurance from Credit Request"); 
    }
    	insuranceRepository.deleteById(id.longValue());
        return "Insurance Deleted Successfully";
    } 
    log.error("Insurance does not exist");
    return "Insurance does not exist" ;
    
}

    @Override
    public String updateInsurance(Integer id, Insurance newInsurance)  {
        if (insuranceRepository.findById(id.longValue()).isPresent()) {
            Insurance oldInsurance = insuranceRepository.findById(id.longValue()).get();
            if (newInsurance.getAmount() != null) {
                oldInsurance.setAmount(newInsurance.getAmount());
            }
            if (newInsurance.getExpiryDate() != null) {
                oldInsurance.setExpiryDate(newInsurance.getExpiryDate());
            }
            if (newInsurance.getType() != null) {
                oldInsurance.setType(newInsurance.getType());
            }
            if (newInsurance.getBeneficiary() != null) {
                oldInsurance.setBeneficiary(newInsurance.getBeneficiary());
            }

            insuranceRepository.save(oldInsurance);
            return "Insurance Updated Successfully";
        } else {
            return "Insurance Not Found";
        }
    }

    @Override
    public Insurance getInsurance(Integer id)  {
        return insuranceRepository.findById(id.longValue()).get();
    }

}
