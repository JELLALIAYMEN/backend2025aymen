package com.DPC.spring.controllers;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.EmploiRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.serviceImpl.EmploiServiceImpl;
import com.DPC.spring.services.MailService;

@RestController
@RequestMapping("emploi")
@CrossOrigin("*")
public class EmploiController {
	@Autowired
	EmploiServiceImpl emploiservice ; 
	@Autowired
	EmploiRepository erpos ; 
	@Autowired
	UtilisateurRepository userrepos ;
	@Autowired
	MailService mail ; 
	
	
	@PostMapping("envoiemail")
	public String envoi(String emailDestinataire, String classe)throws NoSuchAlgorithmException, NoSuchPaddingException {
		List<Emploidetemps> emploiList = this.emploibyclasse(classe); 
		 this.mail.emploie(emailDestinataire, emploiList); 
		 return "true" ;
	}
	
	
	@PostMapping("creeremploi")
	public String Creeremploi(@RequestBody Emploidetemps e , String email , String salle , String matiere , String classe)throws NoSuchAlgorithmException, NoSuchPaddingException {
		return this.emploiservice.Creeremploi(e, email, salle, matiere, classe); 
	}
	@GetMapping("allemploi")
	public List<Emploidetemps>all(){
		return this.erpos.findAll();
	}
	@GetMapping("emploibyuser")
	public List<Emploidetemps>emploibyuser(String email){
	Utilisateur u =this.userrepos.findByEmail(email);	
		return this.erpos.findByUser(u);
	}
	@Autowired
	ClasseRepository crepos ;
	@GetMapping("emploibyclasse")
	public List<Emploidetemps>emploibyclasse(String classe){
		Classe c = this.crepos.findByNomclasse(classe);
		return this.erpos.findByClasse(c);
		
	}
	@Autowired
	EmploiRepository erepo ; 
	@DeleteMapping("supprimer")
	public String delete(Long id) {
		Emploidetemps e = this.erepo.findById(id).get();
		this.erepo.delete(e);
		return "true" ;
	}

}
