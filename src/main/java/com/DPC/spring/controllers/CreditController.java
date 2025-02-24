package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Credit;
import com.DPC.spring.entities.TypePaiement;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.CreditRepository;
import com.DPC.spring.repositories.TypePaiementRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

@RestController
@RequestMapping("credit")
@CrossOrigin("*")

public class CreditController {
@Autowired
CreditRepository creditrepos ; 
@Autowired
UtilisateurRepository userrepos ; 
@Autowired
TypePaiementRepository tprepos ; 

@PostMapping("ajout")
public String ajout(String type , String matricule) {
	TypePaiement tp = this.tprepos.findByType(type);
	Utilisateur u = this.userrepos.findByMatricule(matricule);
	
	Credit c = new Credit();
	c.setTypep(tp);
	c.setUser(u);
	c.setPrix(tp.getPrix());
	this.creditrepos.save(c);
	return "true"  ;
	
	
}
@GetMapping("afficher")
public List <Credit>afficher(){
	return this.creditrepos.findByArchiverIsFalse();
}

@GetMapping("afficherbyclasse")
public List <Credit>afficherbyclasse(Long id){
	return this.creditrepos.creditbyclasse(id);
}
@GetMapping("afficherbymatricule")
public List<Credit>afficherbymatricule(String matricule){
	Utilisateur u = this.userrepos.findByMatricule(matricule);
	return this.creditrepos.findByUser(u);
}

}
