package tn.esprit.softib.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.entity.Payment;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.service.IPaymentService;

@RestController
@RequestMapping(value = "/api/payments")
public class PaymentController {
	
	@Autowired
    IPaymentService paymentService;

	
	

    //delete payment
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deletePayment(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(paymentService.deletePayment(id), HttpStatus.OK);
    }

    
    //Update payment
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updatePayment(@PathVariable(value = "id") Integer id, @RequestBody Payment payment) throws Exception {
        return new ResponseEntity<>(paymentService.updatePayment(id, payment), HttpStatus.OK);
    }

    
    //get payment byID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Payment> getPayment(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(paymentService.getPayment(id), HttpStatus.OK);
    }

}
