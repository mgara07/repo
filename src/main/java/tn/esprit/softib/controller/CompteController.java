package tn.esprit.softib.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.SendFailedException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.softib.Response.CompteResponse;
import tn.esprit.softib.Response.ResponseMessage;
import tn.esprit.softib.component.EmailServiceImpl;
import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.repository.CompteRepository;
import tn.esprit.softib.service.ICompteService;
import tn.esprit.softib.util.GeneratePdfReport;


@RestController
@Slf4j
@RequestMapping("/compte")
public class CompteController {
	
	@Autowired
	ICompteService compteService;
	@Autowired
	EmailServiceImpl emailService;
	@Autowired
	CompteRepository compteRepository;
	@Autowired
    private JobLauncher jobLauncher;
	 @Autowired
	    @Qualifier("emailSenderJob")
	    private Job emailSenderJob;
	
	@GetMapping("/findAll")
	@ResponseBody
	public List<Compte> findAll(){
		List<Compte> comptes = (List<Compte>) compteService.getAllComptes();
		return comptes;
	}
	
	@GetMapping("/findById/{id}")
	@ResponseBody
	public Compte findById(@PathVariable("id") Long id){
		Compte compte = compteService.getCompteById(id);
		return compte;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public Compte save(@RequestBody Compte compte){
		Compte compteResult = compteService.addCompte(compte);
		return compteResult;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public void delete(@PathVariable("id") Long id){
		compteService.deleteCompte(id);
	}
	
	@PutMapping("/update")
	@ResponseBody
	public Compte update(@RequestBody Compte compte){
		return compteService.updateCompte(compte);
	}
	
    @GetMapping("/test")
    public ResponseEntity<ResponseMessage> getAllCompte() {
        try {
            emailService.sendSimpleMessage("hello@world.com", "This is the message", "Thank you for registering with us");
        } catch (SendFailedException sendFailedException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("failed to send email"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("email sent"));
    }

    @GetMapping("/count")
    public ResponseEntity<CompteResponse> getTotalCompte() {
        long compteCount = compteRepository.count();

        CompteResponse response = new CompteResponse();
        response.setMessage("success");
        HashMap<String, Long> count = new HashMap<>();
        count.put("total", compteCount);
        response.setResponse(count);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/email-sent")
    public ResponseEntity<CompteResponse> getEmailsSent() {
        long ordersCount = compteRepository.countByEmailsent(true);

        CompteResponse response = new CompteResponse();
        response.setMessage("success");
        HashMap<String, Long> count = new HashMap<>();
        count.put("total", ordersCount);
        response.setResponse(count);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/send/notification")
    public ResponseEntity<ResponseMessage> sendEmails() {
        Random random = new Random();
        int randomWithNextInt = random.nextInt();

        JobParameter param = new JobParameter(String.valueOf(randomWithNextInt));
        JobParameters jobParameters = new JobParametersBuilder().addParameter("unique", param).toJobParameters();
        List<Compte> emailNotSentOrders = compteRepository.findByEmailsentAndStatus(false, true);

        if (emailNotSentOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Nothing to send"));
        }

        try {
            final JobExecution jobExecution = jobLauncher.run(emailSenderJob, jobParameters);
                Date create = jobExecution.getStartTime();
                Date end = jobExecution.getEndTime();
                int diff = end.getSeconds() - create.getSeconds();
                log.debug("difference = {}", diff);
                TimeUnit.SECONDS.sleep(diff);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("success"));
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException | JobParametersInvalidException |InterruptedException | JobRestartException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }
    }
    
    @RequestMapping(value = "/pdfreport/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> compteReport(@PathVariable("id") Long id) throws MalformedURLException, IOException, DocumentException {
    	
    	
    	Compte compte = compteService.getCompteById(id);

    	List<Compte> comptes = (List<Compte>) compteService.getAllComptes();
    	

        ByteArrayInputStream bis = GeneratePdfReport.comptesReport(compte);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
    @PostMapping("/upload/{id}")
    public ResponseEntity<ResponseMessage> uploadFile(
    		@PathVariable("id") long id,
    		@RequestParam("file") MultipartFile file) {
      String message = "";
      try {
    	compteService.store(id, file);
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }
    
  /*  @DeleteMapping("/auto")
    public ResponseEntity<ResponseMessage> autoDelete() {
      String message = "";
      try {
    	compteService.deleteAutoUser();
        message = "user deleted automatically ";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not delete user ! ";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    } */
    
    
    
		
	

}
