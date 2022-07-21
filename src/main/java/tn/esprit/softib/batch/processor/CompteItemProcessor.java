package tn.esprit.softib.batch.processor;

import javax.mail.SendFailedException;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.softib.component.EmailServiceImpl;
import tn.esprit.softib.entity.Compte;

@Slf4j
public class CompteItemProcessor implements ItemProcessor<Compte, Compte>{
	   @Autowired
	    EmailServiceImpl emailService;

	    @Override
	    public Compte process(Compte compteDTO) throws Exception {
	        log.debug("processor: {}", compteDTO);
	        log.debug(compteDTO.getEmail());
	        try {
	            emailService.sendSimpleMessage(compteDTO.getEmail(), "We have new promotion in our bank!", "Thank you for choosing softib application");
	            compteDTO.setEmailsent(true);
	        } catch (SendFailedException sendFailedException) {
	            log.debug("error: {}", sendFailedException.getMessage());
	        }
	        return compteDTO;
	    }

}
