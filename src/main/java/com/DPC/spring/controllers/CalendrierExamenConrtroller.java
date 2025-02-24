package com.DPC.spring.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.CalendrierExamenReposository;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Calendrierexamen;
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
	ClasseRepository crepos ;
	@Autowired
	MailService mailservice ;
	@PostMapping("/creer")
	public String Creercalendrier(@RequestBody Calendrierexamen calendrier , String email , String salles , String matiere , String classe, String typecalendrier,Trimestre t )throws NoSuchAlgorithmException, NoSuchPaddingException {
		return this.service.Creercalendrier(calendrier, email, salles, matiere, classe,typecalendrier,t);
	}
	@GetMapping("/afficher")
	public List<Calendrierexamen> all(){
		List<Calendrierexamen>all =this.calendrierrepos.findAll();
		return all ;

	}

	@PostMapping("/envoyeremail")
	public String envoi(String email, String classe, Trimestre trimestre, String typecalendrier) {

		Classe c = this.crepos.findByNomclasse(classe);
		List<Calendrierexamen>all =this.calendrierrepos.findByClasseAndTrimestreAndTypecalendrier(c,trimestre,typecalendrier);
		this.mailservice.envoyerCalendrier(email,all,trimestre,typecalendrier);
		return "true" ;

	}


	@Autowired
	UtilisateurRepository userrepos ;
	@GetMapping("/afficherbyprof")
	public ResponseEntity<List<Calendrierexamen>> afficherbyprof(@RequestParam String email) {
		Utilisateur u = this.userrepos.findByEmail(email);

		if (u==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Utilisateur non trouvé
		}

		List<Calendrierexamen> calendriers = this.calendrierrepos.findByUser(u);
		return ResponseEntity.ok(calendriers); // Retourner les calendriers de l'utilisateur
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