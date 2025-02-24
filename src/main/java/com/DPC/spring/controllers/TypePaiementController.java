package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.TypePaiement;
import com.DPC.spring.repositories.TypePaiementRepository;

@RestController
@RequestMapping("typepaiement")
@CrossOrigin("*")
public class TypePaiementController {
@Autowired
TypePaiementRepository typemenurepos ; 


@PostMapping("ajout")
public String ajout(@RequestBody TypePaiement t) {
	this.typemenurepos.save(t);
	return "true" ;
	
}
@GetMapping("afficher")
public List<TypePaiement> afficher(){
	return this.typemenurepos.findAll();
}
}

