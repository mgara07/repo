package tn.esprit.softib.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.softib.enums.CreditStatus;
import tn.esprit.softib.enums.TypeCredit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequest implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
	@Column(name = "cin", nullable = false, length = 8)
    private Long cin;
	@Column(name = "age", nullable = false, length = 3)
	private Integer age;
    private String job;
    private String civilState;
    private Date creationDate;
    private String address;
    private CreditStatus creditRequestStatus;
    private Integer creditTerm;
    private Double creditAmount;
    private Boolean creditRepayment;
    private Double creditRepaymentAmount;
   
    private String rejectionReason;
	@ManyToOne
	private User user;
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
    private TypeCredit type;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "creditRequest")
    @JsonIgnore
    private Credit credit;
    
    private Double netSalary;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Compte compte;
	

}
