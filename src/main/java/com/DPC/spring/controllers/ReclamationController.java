package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Reclamation;
import com.DPC.spring.services.IReclamationService;


@RestController
@RequestMapping("rec")
@CrossOrigin("*")
public class ReclamationController {
@Autowired
IReclamationService service ; 

@PostMapping("/ajouter")
public String reclamer(@RequestBody Reclamation r ,String email) {
	return this.service.reclamer(r, email);
}
@GetMapping("/afficher")
public List<Reclamation>all(){
	return this.service.all();
} 
@GetMapping("/afficherbyid")
public Reclamation reclamatioyid(Long id) {
return this.service.reclamatioyid(id);
}
@GetMapping("/afficherbyemail")
public List<Reclamation> recbyemail(String email ){
	return this.service.recbyemail(email);
	}
@PostMapping("/reponse")
public String reponse(Long id, String reponse) {
	return this.service.reponse(id,reponse);

}
@DeleteMapping("/supprimer")
public String supprimer(Long id) {
	return this.service.supprimer(id);
}


}
