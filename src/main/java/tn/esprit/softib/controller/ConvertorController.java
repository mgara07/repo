package tn.esprit.softib.controller;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.softib.service.ConvertorService;

@RestController
@RequestMapping(value = "/api/convertor")
public class ConvertorController {
	 @Autowired
	   ConvertorService ConvertorService;

	    @PostMapping(value = "/convert")
	    @ResponseBody
	    public MonetaryAmount convert(@RequestParam String currentCurrency, String targetCurrency, BigDecimal amount) {
	        return ConvertorService.convert(currentCurrency, targetCurrency, amount);
	    }

}
