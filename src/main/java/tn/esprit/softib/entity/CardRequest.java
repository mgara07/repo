package tn.esprit.softib.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.softib.enums.CardType;
import tn.esprit.softib.enums.FormStatus;
import tn.esprit.softib.enums.Gender;
import tn.esprit.softib.enums.Nature;
import tn.esprit.softib.enums.Type;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CardSequence")
	private long id;
	private String cin;
	private String rib;
	private String firstName;
	private String lastName;
	private Long phone;
	private String email;
	private CardType cardType;
	private FormStatus formStatus;
	

}
