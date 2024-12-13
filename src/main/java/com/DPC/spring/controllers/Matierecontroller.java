package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Matiere;
import com.DPC.spring.repositories.MatiereRepository;

@RestController
@RequestMapping("matiere")
@CrossOrigin("*")
public class Matierecontroller {
@Autowired
MatiereRepository matrepos ; 
@PostMapping("ajout")
public String ajout(@RequestBody Matiere m) {
	Matiere matiere = this.matrepos.findByNom(m.getNom());
	if(matiere==null) {
		this.matrepos.save(m);
		return "true";
	}
	else {
		return "false" ; 
	}
}


@PostMapping("update")
public String update(@RequestBody Matiere m) {
	Matiere matiere = this.matrepos.findById(m.getId()).get();
	matiere = this.matrepos.saveAndFlush(m);
	return "true" ;
}

@GetMapping("afficher")
public List<Matiere> afficher(){
	return this.matrepos.findAll() ;
}
@GetMapping("afficherbyid")
public Matiere afficherbyid(Long id) {
	return this.matrepos.findById(id).get();
}

}

