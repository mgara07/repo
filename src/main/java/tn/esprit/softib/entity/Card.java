package tn.esprit.softib.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.softib.enums.CardType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CardSequence")
	private long id;
	private String rib;
	private CardType cardType;
	

}
