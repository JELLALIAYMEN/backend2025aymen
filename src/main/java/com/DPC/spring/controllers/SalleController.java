package com.DPC.spring.controllers;

import java.util.List;

import com.DPC.spring.entities.SalleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Salle;
import com.DPC.spring.repositories.SalleRepository;
import com.DPC.spring.services.ISalleService;
@CrossOrigin("*")
@RestController
@RequestMapping("salle")
public class SalleController {

@Autowired
ISalleService salleserviice ;
@Autowired
SalleRepository sallerepos; 

@GetMapping("/affichage")
public List<Salle> affich(){
	return this.salleserviice.afficher();
}
@GetMapping("/affichagebyid")
public Salle affich(Long id){
	return this.salleserviice.afficherbyid(id);
}

@PutMapping("/modif")
public String modif(@RequestParam Long id , @RequestParam String nom) {
	
	return 	this.salleserviice.modif(id, nom);
	
	
}
	@PostMapping("/ajouter")
	public ResponseEntity<String> ajouterSalle(@RequestBody Salle salle, @RequestParam SalleType salleType) {
		String resultat = salleserviice.Ajout(salle, salleType);
		return ResponseEntity.ok(resultat);
	}

}
