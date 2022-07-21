package tn.esprit.softib.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@RestController
public class SmsController {

        @GetMapping(value = "/sendSMS")
        public ResponseEntity<String> sendSMS() {
        	
                //Twilio.init("AC5000345f881795fa87747639935a069c", "346cf975c1404fc505178f98518e4e64");
           

               // Message.creator(new PhoneNumber("+21629407444"),
               //                 new PhoneNumber("+19282725058"), "Hello from Twilio 📞").create();
        	
        	 Twilio.init("AC18b21c0d3b07e651078606d3ed337540", "fe7456087dcd8c473c7bc22ec3678c03");
            

             Message.creator(new PhoneNumber("+21629407444"),
                            new PhoneNumber("+19707143507"), "Hello, We welcome you and thank you for choosing us as your banking servicer 📞").create();
              
                return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
        }
        
    
}