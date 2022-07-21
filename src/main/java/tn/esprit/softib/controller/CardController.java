package tn.esprit.softib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import tn.esprit.softib.entity.Card;
import tn.esprit.softib.entity.CardRequest;
import tn.esprit.softib.service.ICard;
import tn.esprit.softib.service.ICardRequest;

@RestController
@RequestMapping("/card")
public class CardController {

	@Autowired
	ICard icard;
	
	
	@GetMapping("/findAll")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public List<Card> findAll() {
		List<Card> cards = (List<Card>) icard.getAllCards();
		return cards;
	}

	

	@GetMapping("/findById/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Card findById(@PathVariable("id") Long id) {
		Card card = icard.getCardById(id);
		return card;
	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(@PathVariable("id") Long id) {
		icard.deleteCard(id);
	}

	@PutMapping("/update")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Card update(@RequestBody Card newCredit) {
		return icard.updateCard(newCredit);
	}
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Card add(@RequestBody Card newCredit) {
		return icard.addCard(newCredit);
	}

}
