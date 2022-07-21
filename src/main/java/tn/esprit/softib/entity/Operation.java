package tn.esprit.softib.entity;

import java.io.Serializable;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tn.esprit.softib.enums.OperationStatus;
import tn.esprit.softib.enums.OperationSubType;
import tn.esprit.softib.enums.OperationType;
import tn.esprit.softib.enums.TypeTransaction;
@Entity
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="TYPE_OP",discriminatorType=DiscriminatorType.STRING,length=1)
public  class Operation  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    private LocalDate date;
    private BigDecimal amount;
    private Boolean isInternal;
    private OperationType operationtype;
    private OperationSubType operationSubType;
    private OperationStatus  operationStatus;
    private Boolean isArchived;
    @ManyToOne
    @JoinColumn(name = "Compte_Id")
    private Compte compte;

    @OneToMany(mappedBy="operation")
    private Collection<Transaction> transactions;

    public Operation(LocalDate date, BigDecimal v, boolean b, OperationType retrieve, OperationSubType regluement_credit, OperationStatus toBeExecuted) {
        this.date = date;
        this.amount = v;
        this.isInternal = b;
        this.operationtype = retrieve;
        this.operationSubType = regluement_credit;
        this.operationStatus = toBeExecuted;
    }



    public void addTransactions(Collection<Transaction> transactions){
        this.transactions.addAll(transactions);
   }



}
