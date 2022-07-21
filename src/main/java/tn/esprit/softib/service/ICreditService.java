package tn.esprit.softib.service;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.entity.Payment;

public interface ICreditService {
	public Credit addPayment(Payment payment, Integer creditId);
	public Credit addCredit(Credit credit);
	public String deleteCredit(Integer id);
	 public String updateCredit(Integer id, Credit newCredit);
	 public Credit getCredit(Integer id);

}
