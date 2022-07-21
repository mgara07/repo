package tn.esprit.softib.validator;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.softib.entity.Transaction;


public class TransactionValidator {
    public static List<String> validate(Transaction transaction) {
        List<String> errors = new ArrayList<>();
        if (transaction == null) {
            errors.add("Can you set The Transaction Infos");
            return errors;
        }
        return errors;
    }
}
