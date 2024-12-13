package com.DPC.spring.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Calendrierexamen;
import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.CalendrierExamenReposository;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.ICalendrierExamenService;

@RestController
@RequestMapping("calendrier")
@CrossOrigin("*")
public class CalendrierExamenConrtroller {
@Autowired
ICalendrierExamenService service ; 
@Autowired
CalendrierExamenReposository calendrierrepos ; 
@Autowired
ClasseRepository  crepos ;
@PostMapping("/creer")
public String Creercalendrier(@RequestBody Calendrierexamen calendrier , String email , String salles , String matiere , String classe, String typecalendrier )throws NoSuchAlgorithmException, NoSuchPaddingException {
	return this.service.Creercalendrier(calendrier, email, salles, matiere, classe,typecalendrier);
}
@GetMapping("/afficher")
public  List<Calendrierexamen>all(){
	return this.calendrierrepos.findAll(); 
}
@Autowired
UtilisateurRepository userrepos ;
@GetMapping("/afficherbyprof")
public  List<Calendrierexamen>afficherbyprof(String email){
	Utilisateur u =this.userrepos.findByEmail(email);
	return this.calendrierrepos.findByUser(u); 
}
@GetMapping("/afficherbyclasse")
public  List<Calendrierexamen>allbyclasse(String classe){
	Classe c = this.crepos.findByNomclasse(classe);
	return this.calendrierrepos.findByClasse(c);
}
@DeleteMapping("supprimer")
public String supp(Long id) {
	Calendrierexamen c =this.calendrierrepos.findById(id).get();
	this.calendrierrepos.delete(c);
	return "true" ;
}
	
}
