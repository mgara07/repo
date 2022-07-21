package tn.esprit.softib.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import tn.esprit.softib.entity.Operation;
import tn.esprit.softib.enums.OperationStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Api("operations")
public interface OperationAPI {
	//@PostMapping("/save")
    @PostMapping(value = "/operations/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a Operation", notes = "this methode can add new Operation", response = Operation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Object added "),
            @ApiResponse(code = 400, message = "Operation Object is invalid")
    })
    
    ResponseEntity<Operation> save(@RequestBody Operation operation);

    @PatchMapping(value =  "/operations/update/status/{idOperation}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify credit status", notes = "this methode can modify operations status", response = Operation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "operation status modified "),
            @ApiResponse(code = 400, message = "operation status is invalid")
    })
    ResponseEntity<Operation> updateCreditStatus(@PathVariable("idOperation") Integer idCredit, @RequestParam("status") OperationStatus operationStatus);

    @GetMapping(value = "/operations/findbyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Operation by id", notes = "This methode Get Operation by id ", responseContainer = "Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Operation / Void list")
    })
    Operation getOperationById(@PathVariable("id") Integer id);

    @GetMapping(value = "/operations/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all operations by archive", notes = "This methode get all operations by archive ", responseContainer = "List<Operation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    List<Operation> findAllByArchived(@RequestParam("archived") Boolean archived);

    @GetMapping(value ="/operations/account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all operations by user id", notes = "This methode get all operations by id user ", responseContainer = "List<Operation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of operations / Void list")
    })
    List<Operation> findAllByUser(@PathVariable("id") Integer id);

    @GetMapping(value = "/operations/{id}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all operations by user id", notes = "This methode get all operations by id client and status ", responseContainer = "List<Operation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of operations / Void list")
    })
    List<Operation> getAllOperationByClientAndStatus(@PathVariable("id") Integer id,@PathVariable("status") OperationStatus operationStatus);

 

}
