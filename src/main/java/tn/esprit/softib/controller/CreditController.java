package tn.esprit.softib.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.service.ICreditService;
import tn.esprit.softib.service.IPaymentService;

@RestController
@RequestMapping(value = "/api/credits")
public class CreditController {
	
	@Autowired
	ICreditService creditService;
	
	@Autowired
	IPaymentService paymentService;
	


   //Delete credit
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deleteCredit(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditService.deleteCredit(id), HttpStatus.OK);
    }

	//Update Credit
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateCredit(@PathVariable(value = "id") Integer id, @RequestBody Credit credit) throws Exception {
        return new ResponseEntity<>(creditService.updateCredit(id, credit), HttpStatus.OK);
    }
    
    //Get Credit
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Credit> getCredit(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditService.getCredit(id), HttpStatus.OK);
    }

    //Pay Credit
    @PutMapping(value = "pay/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> pay(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(paymentService.pay(id), HttpStatus.OK);
    }

    
}
