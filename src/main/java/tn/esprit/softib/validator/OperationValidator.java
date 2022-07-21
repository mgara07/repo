package tn.esprit.softib.validator;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.softib.entity.Operation;


public class OperationValidator {
	 public static List<String> validate(Operation operation) {
	        List<String> errors = new ArrayList<>();
	        if (operation == null) {
	            errors.add("Can you set The Operations Infos");
	            return errors;
	        }
	        return errors;
	    }

}
