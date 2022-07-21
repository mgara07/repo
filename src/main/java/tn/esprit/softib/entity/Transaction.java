package tn.esprit.softib.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.softib.enums.TypeTransaction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	   private LocalDate date;
	    private TypeTransaction transactionType;
	    private Boolean isNegativeTx;
	    private Boolean isRevertedTransaction;
	    private BigDecimal movement;

	    @ManyToOne
	  @JoinColumn(name = "Operation_Id")
	    private Operation operation;

	    public Transaction(LocalDate date, TypeTransaction txtype, boolean b1, Operation operation, BigDecimal m) {
	        this.date = date;
	        this.transactionType =txtype;
	        this.isNegativeTx = b1;
	        this.operation = operation;
	        this.movement = m;
	    }

	 
}
