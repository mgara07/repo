package tn.esprit.softib.service;

import java.util.List;

import tn.esprit.softib.entity.Card;


public interface ICard {
	public List<Card> getAllCards();
	public Card getCardById(long id);
	public Card addCard(Card card);
	public void deleteCard(long id);
	public Card updateCard(Card card);
	

}
