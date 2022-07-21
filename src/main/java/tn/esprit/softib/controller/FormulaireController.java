package tn.esprit.softib.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import tn.esprit.softib.entity.ConfirmationMessage;
import tn.esprit.softib.entity.FormByUserStat;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.enums.Status;
import tn.esprit.softib.helper.ExcelHelper;
import tn.esprit.softib.payload.response.MessageResponse;
import tn.esprit.softib.service.ExcelService;
import tn.esprit.softib.service.IFormulaireService;

@RestController
@RequestMapping("/formulaire")
public class FormulaireController {

	private static final String FILENAME = "formulaires.xlsx";
	private static final String PLEASE_UPLOAD_AN_EXCEL_FILE = "Please upload an excel file!";
	private static final String EXCLAMATION = "!";
	private static final String COULD_NOT_UPLOAD_THE_FILE = "Could not upload the file: ";
	private static final String FORMS_WHERE_UPLOADED = " forms where uploaded";
	private static final String VIRGULE = " ,";
	private static final String UPLOADED_THE_FILE_SUCCESSFULLY = "Uploaded the file successfully: ";
	private static final String EMPTY = "";
	@Autowired
	IFormulaireService formulaireService;
	@Autowired
	ExcelService fileService;

	@GetMapping("/findAll")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public List<Formulaire> findAll() {
		List<Formulaire> forms = (List<Formulaire>) formulaireService.getAllFormulaires();
		return forms;
	}

	@GetMapping("/findByEmail/{email}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Formulaire findByEmail(@PathVariable("email") String email) {
		Formulaire form = formulaireService.getFormulaireByEmail(email);
		return form;
	}

	@GetMapping("/findById/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Formulaire findById(@PathVariable("id") Long id) {
		Formulaire form = formulaireService.getFormulaireById(id);
		return form;
	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(@PathVariable("id") Long id) {
		formulaireService.deleteFormulaire(id);
	}

	@PutMapping("/update")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Formulaire update(@RequestBody Formulaire newFrom) {
		return formulaireService.updateFormulaire(newFrom);
	}

	@PostMapping("/confirmFormulaire/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> confirmFormulaire(@PathVariable("id") Long id) {
		ConfirmationMessage confMsg = formulaireService.confirmFormulaire(id);
		if (confMsg.getStatus().equals(Status.OK)) {
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(confMsg.getMessage()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse(confMsg.getMessage()));
		}

	}

	@PostMapping("/rejectFormulaire/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public Formulaire rejectFormulaire(@PathVariable("id") Long id) {
		return formulaireService.rejectFormulaire(id);
	}

	@PostMapping("/upload")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = EMPTY;
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				int uploadedForms = fileService.importExcel(file);
				message = UPLOADED_THE_FILE_SUCCESSFULLY + file.getOriginalFilename() + VIRGULE + uploadedForms
						+ FORMS_WHERE_UPLOADED;
				return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
			} catch (Exception e) {
				message = COULD_NOT_UPLOAD_THE_FILE + file.getOriginalFilename() + EXCLAMATION;
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
			}
		}
		message = PLEASE_UPLOAD_AN_EXCEL_FILE;
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
	}

	@GetMapping("/getStatistics")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<FormByUserStat>> getStats() {
		List<FormByUserStat> stats = formulaireService.getUserFormsStats();
		return ResponseEntity.status(HttpStatus.OK).body(stats);
	}

	@GetMapping("/export")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Resource> getFile() {
		String filename = FILENAME;
		InputStreamResource file = new InputStreamResource(fileService.exportExcel());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	

}
