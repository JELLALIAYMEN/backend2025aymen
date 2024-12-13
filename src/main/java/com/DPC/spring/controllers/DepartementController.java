package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Departement;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.services.IDepartementService;

@RestController
@RequestMapping("departement")
@CrossOrigin("*")
public class DepartementController {
@Autowired
IDepartementService depservice ; 
@Autowired
DepartementRepository deparrepos ;

@PostMapping("ajout")
public String Ajout(@RequestBody Departement d ) {
	return this.depservice.Ajout(d);
	
}
@GetMapping("afficherbyid")
public Departement afficherbyid(Long id) {
	return this.deparrepos.findById(id).get();
}
@PostMapping("update")
public String update(@RequestBody Departement d) {
	System.out.println(d.getId()+"****");
	Departement dep = this.deparrepos.findById(d.getId()).get();
	dep =this.deparrepos.saveAndFlush(d);
	return "true"; 
}
@GetMapping("afficher")

public List<Departement>all(){
	return this.deparrepos.findAll(); 
}
}
