package tn.esprit.softib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.softib.Response.ResponseMessage;
import tn.esprit.softib.entity.Card;
import tn.esprit.softib.entity.CardRequest;
import tn.esprit.softib.entity.ConfirmationMessage;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.enums.FormStatus;
import tn.esprit.softib.enums.Status;
import tn.esprit.softib.payload.response.MessageResponse;
import tn.esprit.softib.service.ICardRequest;

@RestController
@RequestMapping("/cardRequest")
public class CardRequestController {
	
	@Autowired
	ICardRequest icardRequest;
	
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT') or hasRole('AGENT')")
	public CardRequest add(@RequestBody CardRequest newCard) {
		newCard.setFormStatus(FormStatus.PENDING);
		return icardRequest.addCardRequest(newCard);
	}
	
	@GetMapping("/findAll")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public List<CardRequest> findAll() {
		List<CardRequest> cardRequests = (List<CardRequest>) icardRequest.getAllCardRequests();
		return cardRequests;
	}
	
	
	@GetMapping("/findById/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public CardRequest findById(@PathVariable("id") Long id) {
		CardRequest cardRequest = icardRequest.getCardRequestById(id);
		return cardRequest;
	}

	
	@GetMapping("/findByRib/{rib}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public CardRequest findByRib(@PathVariable("rib") String rib) {
		CardRequest cardRequest = icardRequest.getCardRequestByRib(rib);
		return cardRequest;
	}
	
	@PostMapping("/confirmCardRequest/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> confirmRequestCard(@PathVariable("id") Long id) {
		ConfirmationMessage confMsg = icardRequest.confirmCardRequest(id);
		if (confMsg.getStatus().equals(Status.OK)) {
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(confMsg.getMessage()));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse(confMsg.getMessage()));
		}
		
	}
	
	
	@PutMapping("/update")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public CardRequest updateController(@RequestBody CardRequest newCardRequest) {
		newCardRequest.setFormStatus(FormStatus.PENDING);
		return icardRequest.updateCardRequest(newCardRequest);
	}
	


	@DeleteMapping("/delete/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(@PathVariable("id") Long id) {
		icardRequest.deleteCardRequest(id);
	}
	
	@PostMapping("/rejectRequestCard/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public CardRequest rejectRequestCard(@PathVariable("id") Long id) {
		return icardRequest.rejectCardRequest(id);
	}
	
	 @DeleteMapping("/auto")
	    public ResponseEntity<ResponseMessage> autoDelete() {
	      String message = "";
	      try {
	    	 icardRequest.deleteAutoCard();
	    	 
	        message = "card request  deleted automatically ";
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	        message = "Could not delete card request ! ";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }


	


	
	
}
