package tn.esprit.softib.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;

import javax.persistence.Lob;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.softib.enums.Nature;
import tn.esprit.softib.enums.TypeTransaction;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nomComplet;
	private Nature natureCompte;
	private String rib;
	private String iban;
	private String codeBic;
	private BigDecimal solde;
	private String email;
	private boolean emailsent;
	private boolean status;
	@Lob
	private byte[] data;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy="compte")
	private Set<Credit> credits;
	 @OneToMany(mappedBy="compte")
	    private Collection<Operation> operations;

	
	
}
