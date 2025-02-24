package com.DPC.spring.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.EmploiRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.serviceImpl.EmploiServiceImpl;
@CrossOrigin("*")
@RestController
@RequestMapping("emploi")
public class EmploiController {
	@Autowired
	EmploiServiceImpl emploiservice ;

	@Autowired
	EmploiRepository erpos ;
	@Autowired
	UtilisateurRepository userrepos ;
	@Autowired
	MailService mail ;


	@PostMapping("/creeremploi")
	public String Creeremploii(
			@RequestBody Emploidetemps e,
			@RequestParam String email,
			@RequestParam String salle,
			@RequestParam String matiere,
			@RequestParam String classe
	)throws NoSuchAlgorithmException, NoSuchPaddingException {
		return this.emploiservice.Creeremploi(e, email, salle, matiere, classe); 
	}

	@GetMapping("allemploi")
	public List<Emploidetemps> all() {
		List<Emploidetemps> emplois = this.erpos.findAll();
		System.out.println("Emplois: " + emplois);  // Log pour voir ce qui est renvoyé
		return emplois;
	}

	@GetMapping("/emploibyuser")
	public ResponseEntity<List<Emploidetemps>> emploibyuser(@RequestParam String email) {
		Utilisateur u = this.userrepos.findByEmail(email);

		if (u==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Utilisateur non trouvé
		}

		List<Emploidetemps> emplois = this.erpos.findByUser(u);
		return ResponseEntity.ok(emplois); // Retourner l'emploi du temps de l'utilisateur
	}


	@Autowired
	ClasseRepository crepos ;
	@GetMapping("emploibyclasse")
	public List<Emploidetemps>emploibyclasse(String classe){
		Classe c = this.crepos.findByNomclasse(classe);
		return this.erpos.findByClasse(c);

	}
	@PostMapping("envoiemail")
	public String envoi(String emailDestinataire, String classe)throws NoSuchAlgorithmException, NoSuchPaddingException {
		List<Emploidetemps> emploiList = this.emploibyclasse(classe);
		this.mail.emploie(emailDestinataire, emploiList);
		return "true" ;
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


