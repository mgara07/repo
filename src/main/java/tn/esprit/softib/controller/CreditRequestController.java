package tn.esprit.softib.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.softib.entity.Credit;
import tn.esprit.softib.entity.CreditRequest;
import tn.esprit.softib.service.ICreditRequestService;
import tn.esprit.softib.service.IPaymentService;

@RestController
@RequestMapping(value = "/api/creditrequests")
public class CreditRequestController {
	
	@Autowired
    ICreditRequestService creditRequestService;

    @Autowired
    IPaymentService paymentService;

    
    //Create Credit Request
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreditRequest> createCreditRequest(@RequestBody CreditRequest creditRequest, @PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditRequestService.addCreditRequest(creditRequest,id), HttpStatus.OK);
    }

    
    //Delete Credit Request
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deleteCreditRequest(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditRequestService.deleteCreditRequest(id), HttpStatus.OK);
    }

    //Update Credit Request
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateCreditRequest(@PathVariable(value = "id") Integer id, @RequestBody CreditRequest creditRequest) throws Exception {
        return new ResponseEntity<>(creditRequestService.updateCreditRequest(id, creditRequest), HttpStatus.OK);
    }

    
    //Get Credit Request By ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreditRequest> getCreditRequest(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditRequestService.getCreditRequest(id), HttpStatus.OK);
    }

    
    
    // create credit from credit request
    @PostMapping(value = "create-credit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> acceptCreditRequest(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditRequestService.createCreditFromCreditRequest(id), HttpStatus.OK);
    }

    
    //reject creditRequest
    @PutMapping(value = "reject/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> rejectCreditRequest(@PathVariable(value = "id") Integer id, @RequestBody CreditRequest creditRequest) throws Exception {
        return new ResponseEntity<>(creditRequestService.rejectCreditRequest(id, creditRequest), HttpStatus.OK);
    }

    //Client Confirm credit request
    @PutMapping(value = "client-accept/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> acceptCreditRequestChanges(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditRequestService.acceptCreditRequestChanges(id), HttpStatus.OK);
    }

    
    @PutMapping(value = "treat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> treatCreditRequest(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(creditRequestService.treatCreditRequest(id), HttpStatus.OK);
    }

    
    // get all accepted credits requests
    @GetMapping(value = "/client-accepted", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<CreditRequest>> getAllCreditRequestAcceptedFromClients() throws Exception {
        return new ResponseEntity<List<CreditRequest>>(creditRequestService.getAllCreditRequestAcceptedFromClients(), HttpStatus.OK);
    }

}
