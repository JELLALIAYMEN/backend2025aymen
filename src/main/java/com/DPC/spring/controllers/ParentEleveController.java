package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.ParentEleve;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ParentElveRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

@RestController
@RequestMapping("parent")
@CrossOrigin("*")
public class ParentEleveController {
@Autowired
ParentElveRepository perepos ; 
@Autowired
UtilisateurRepository userrepos ; 

@PostMapping("affecter")
public String affecter(String emailparent,String emaileleve) {
	Utilisateur ue = this.userrepos.findByEmail(emaileleve);
	Utilisateur up= this.userrepos.findByEmail(emailparent);
	ParentEleve parent = this.perepos.findByParentAndEleve(up,ue);
	if(parent ==null) {
	ParentEleve PE = new ParentEleve();
	
	PE.setEleve(ue);
	PE.setParent(up);
	this.perepos.save(PE);
	return "true" ; }
	else {
		return "false" ;
	}
}
@GetMapping("affichermeseleve")
public List<ParentEleve>affichermeseleve(String emailparent){
	Utilisateur up= this.userrepos.findByEmail(emailparent);
	return this.perepos.findByParent(up);
}
}
