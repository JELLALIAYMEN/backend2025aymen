package com.DPC.spring.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Payement;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.PayementRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

@RequestMapping("pay")
@RestController
@CrossOrigin("*")
public class PayementController {
@Autowired
PayementRepository payrepos ; 
@Autowired
UtilisateurRepository userrepos ;
@PostMapping("ajout")
public String Ajout(@RequestBody Payement p , String email) {
	Utilisateur u = this.userrepos.findByEmail(email);
	p.setUser(u);
	p.setDate(new Date(System.currentTimeMillis()));

	this.payrepos.save(p);
	return "true" ; 
}

@GetMapping("all")
public List<Payement>all()
{
	return this.payrepos.findAll();}

@GetMapping("allbyuser")
public List<Payement> allbyuser(String email){
	Utilisateur u = this.userrepos.findByEmail(email);
	return this.payrepos.findByUser(u);
}
}
