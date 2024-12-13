package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Menu;
import com.DPC.spring.entities.Reservationmenu;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.MenuRepository;
import com.DPC.spring.repositories.ReservationMenuRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

@RestController
@RequestMapping("reservation")
@CrossOrigin("*")
public class ReservationMenuController {

@Autowired
ReservationMenuRepository reservationrepos ; 
@Autowired
MenuRepository menurepos ;
@Autowired
UtilisateurRepository userrepos ;


@PostMapping("creer")
public String creer(Long idmenu , String email) {
	Menu m = this.menurepos.findById(idmenu).get();
	Utilisateur u = this.userrepos.findByEmail(email);
	Reservationmenu r = new Reservationmenu();
	r.setEleve(u);
	r.setMenu(m);
	this.reservationrepos.save(r);
	return "true" ; 
}
@GetMapping("/afficher")
public List<Reservationmenu> all(){
	return this.reservationrepos.findAll(); 
}
@GetMapping("/afficherbyemail")
public List<Reservationmenu> allbyemail(String email){
	Utilisateur u = this.userrepos.findByEmail(email);

	return this.reservationrepos.findByEleve(u);
}
}
