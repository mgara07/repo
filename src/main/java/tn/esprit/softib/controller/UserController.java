package tn.esprit.softib.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.softib.entity.BanUserDescription;
import jdk.internal.org.jline.utils.Log;
import tn.esprit.softib.Response.ResponseMessage;
import tn.esprit.softib.entity.Formulaire;
import tn.esprit.softib.entity.User;
import tn.esprit.softib.service.IFormulaireService;
import tn.esprit.softib.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping("/findAll")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> findAll(){
		List<User> users = (List<User>) userService.getAllUsers();
		return users;
	}
	
	@GetMapping("/findClients")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> findClients(){
		List<User> users = (List<User>) userService.getClients();
		return users;
	}
	
	@GetMapping("/findByEmail/{email}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public User findByEmail(@PathVariable("email") String email){
		User user = userService.getUserByEmail(email);
		return user;
	}
	
	@GetMapping("/findByCin/{cin}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public User findByCin(@PathVariable("cin") String cin){
		User user = userService.getUserByCin(cin);
		return user;
	}
	
		
	@GetMapping("/findById/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public User findById(@PathVariable("id") Long id){
		User user = userService.getUserById(id);
		return user;
	}
	
	@PostMapping("/save")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public User save(@RequestBody User userBody){
		User user = userService.addUser(userBody);
		return user;
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(@PathVariable("id") Long id){
		userService.deleteUser(id);
	}
	
	@PutMapping("/update")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public User update(@RequestBody User newUser){
		return userService.updateUser(newUser);
	}
	
	@PutMapping("/signUser/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public User signUser(@PathVariable("id") Long id){
		return userService.signUser(id);
	}
	
	 @DeleteMapping("/auto")
	    public ResponseEntity<ResponseMessage> autoDelete() {
	      String message = "";
	      try {
	    	  userService.deleteAutoUser();
	    	  Log.debug(userService.deleteAutoUser());
	        message = "user deleted automatically ";
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	        message = "Could not delete user ! ";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }
	

	@PutMapping("/banUser")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public User banUser(@RequestBody BanUserDescription banUserDesc){
		return userService.banUser(banUserDesc.getUsername(),banUserDesc.getBanRaison());
	}
}
