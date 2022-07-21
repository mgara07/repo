package tn.esprit.softib.service;

import tn.esprit.softib.entity.Payment;

public interface IPaymentService {
	
	public String deletePayment(Integer id);
	public String updatePayment(Integer id, Payment newPayment) ;
	public Payment getPayment(Integer id);
	public String pay(Integer creditId);

}
