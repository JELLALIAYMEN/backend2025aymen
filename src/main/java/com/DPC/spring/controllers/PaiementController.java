package com.DPC.spring.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Credit;
import com.DPC.spring.entities.Paiement;
import com.DPC.spring.repositories.CreditRepository;
import com.DPC.spring.repositories.PaiementRepository;
@RequestMapping("paiement")
@RestController
@CrossOrigin("*")
public class PaiementController {
	
	@Autowired
	CreditRepository crepos ; 
	@Autowired
	PaiementRepository prepos; 
	
	@PostMapping("payer")
	public String payer(Long id , @RequestBody Paiement p ) {
		Credit c = this.crepos.findById(id).get();
		if(p.getPrix()>c.getPrix()) {
			return "fase";
		}
		p.setDate(new Date(System.currentTimeMillis()));	
		p.setCredit(c);
		this.prepos.save(p);
		c.setPrix(c.getPrix()-p.getPrix());
		this.crepos.save(c);
		if (c.getPrix()==0) {
		c.setArchiver(true);	
		this.crepos.save(c);
		}
		
		return "true"; 
		
	}

}
