package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.repositories.ClasseRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("classe")
public class ClasseController {
@Autowired
ClasseRepository classerepos ; 

@PostMapping("/ajouter")
public String ajouter(@RequestBody Classe c) {
	Classe cexiste = this.classerepos.findByNomclasse(c.getNomclasse());
	if(cexiste==null) {
		this.classerepos.save(c);
		return "true";
	}
	else {
		return "false";
	}
}
	@GetMapping("/afficher")
	public List<Classe>afficher(){
		return this.classerepos.findAll();
	}

	@GetMapping("/count-classes")
	public Long allclasses(){
		return this.classerepos.countAll();
	}
}
