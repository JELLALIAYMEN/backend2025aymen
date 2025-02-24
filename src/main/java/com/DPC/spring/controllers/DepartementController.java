package com.DPC.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Departement;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.services.IDepartementService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("departement")
public class DepartementController {
@Autowired
IDepartementService depservice ; 


@PostMapping("ajout")
public String Ajout(@RequestBody Departement d ) {
	return this.depservice.Ajout(d);
	
}
	@GetMapping("/all")
	public List<Departement> findAllDepartement() {
		return  depservice.findAllDepartement();
	}
}
