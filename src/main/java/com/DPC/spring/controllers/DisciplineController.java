package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Discipline;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.DisciplineRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

@RequestMapping("discipline")
@RestController
@CrossOrigin("*")
public class DisciplineController {
@Autowired
DisciplineRepository disrepos ; 
@Autowired
UtilisateurRepository userrepos ; 

@PostMapping("/creer")
public String Ajout(String type , String emailprof,String emaileleve) {
	Utilisateur ue= this.userrepos.findByEmail(emaileleve);
	Utilisateur up = this.userrepos.findByEmail(emailprof);
	Discipline d = new Discipline();
	d.setEleve(ue);
	d.setEleve(up);

	//d.setUser(up);
	//d.setTypeDisc(type);
	this.disrepos.save(d);
	return "true"; 
}
@GetMapping("alll")
public List<Discipline>all(){
	return this.disrepos.findAll();
}
@GetMapping("allbyelleve")
public List<Discipline>allbyeleve(String email){
	Utilisateur ue= this.userrepos.findByEmail(email);
	return this.disrepos.findByEleve(ue);
}
@GetMapping("allbyprof")
public List<Discipline>allbyparent(String email){
	Utilisateur up= this.userrepos.findByEmail(email);
	return this.disrepos.findByUser(up);
	
}
}
