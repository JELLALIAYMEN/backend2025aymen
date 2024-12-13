package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Salle;
import com.DPC.spring.repositories.SalleRepository;
import com.DPC.spring.services.ISalleService;

@RestController
@RequestMapping("salle")
@CrossOrigin("*")
public class SalleController {

@Autowired
ISalleService salleserviice ;
@Autowired
SalleRepository sallerepos; 
@PostMapping("/ajout")
public String Ajout(@RequestBody Salle s , String nomdep) {

	return this.salleserviice.Ajout(s, nomdep);
} 
@GetMapping("/affichage")
public List<Salle> affich(){
	return this.salleserviice.afficher();
}
@GetMapping("/affichagebyid")
public Salle affich(Long id){
	return this.salleserviice.afficherbyid(id);
}

@PutMapping("/modif")
public String modif(@RequestBody Salle s,String nomdep) {
	System.out.println(nomdep);
	
	return 	this.salleserviice.modif(s,nomdep);
	
	
}


}
