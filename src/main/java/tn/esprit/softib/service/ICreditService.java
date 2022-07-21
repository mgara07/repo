package tn.esprit.softib.service;

import tn.esprit.softib.entity.Credit;

public interface ICreditService {
	public Credit addCredit(Credit credit);
	public String deleteCredit(Integer id);
	 public String updateCredit(Integer id, Credit newCredit);
	 public Credit getCredit(Integer id);

}
