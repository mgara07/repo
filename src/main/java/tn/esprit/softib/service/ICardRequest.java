package tn.esprit.softib.service;

import java.util.List;

import tn.esprit.softib.entity.CardRequest;
import tn.esprit.softib.entity.ConfirmationMessage;



public interface ICardRequest {
	public List<CardRequest> getAllCardRequests();
	public CardRequest getCardRequestById(long id);
	public CardRequest getCardRequestByRib(String rib);
	public CardRequest addCardRequest(CardRequest cardRequest);
	public void deleteCardRequest(long id);
	public CardRequest updateCardRequest(CardRequest cardRequest);
	public ConfirmationMessage confirmCardRequest(long id);
	public CardRequest rejectCardRequest(long id);
	public void deleteAutoCard();

}
