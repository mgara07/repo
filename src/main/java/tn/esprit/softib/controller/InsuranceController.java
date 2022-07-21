package tn.esprit.softib.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import jdk.internal.org.jline.utils.Log;
import tn.esprit.softib.entity.Insurance;
import tn.esprit.softib.service.IInsuranceService;
import tn.esprit.softib.service.InsuranceServiceImpl;

@RestController
@RequestMapping(value = "/api/insurances")
@Slf4j
public class InsuranceController {
	@Autowired
    IInsuranceService insuranceService;

	//file upload
	@Value("${file.upload-dir}")
	String FILE_DIRECTORY;
	
	@PostMapping("/uploadFile")
	public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file) throws IOException{
		File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
		myFile.createNewFile();
		FileOutputStream fos =new FileOutputStream(myFile);
		fos.write(file.getBytes());
		fos.close();
		return new ResponseEntity<Object>("The File Uploaded Successfully", HttpStatus.OK);
	}
	
	//Create Insurance
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createInsurance(@PathVariable(value = "id") Integer id) throws Exception {
    	log.info("Insurance Created");
        return new ResponseEntity<>(insuranceService.addInsurance(id), HttpStatus.OK);
    }

    //Delete Insurance
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> deleteInsurance(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(insuranceService.deleteInsurance(id), HttpStatus.OK);
    }

    //Update Insurance
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateInsurance(@PathVariable(value = "id") Integer id, @RequestBody Insurance insurance) throws Exception {
        return new ResponseEntity<>(insuranceService.updateInsurance(id, insurance), HttpStatus.OK);
    }

    
    //Get Insurance By ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Insurance> getInsurance(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(insuranceService.getInsurance(id), HttpStatus.OK);
    }

}
