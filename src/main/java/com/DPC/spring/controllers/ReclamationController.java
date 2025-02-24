package com.DPC.spring.controllers;

import java.util.Date;
import java.util.List;

import com.DPC.spring.DTO.ReclamationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Reclamation;
import com.DPC.spring.services.IReclamationService;

@CrossOrigin("*")
@RestController
@RequestMapping("rec")
public class ReclamationController {
	@Autowired
	IReclamationService service ;
	@PostMapping("/ajouter")
	public ResponseEntity<ReclamationDTO> reclamer(@RequestParam String email, @RequestParam String matricule, @RequestParam String sujet, @RequestParam   @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		ReclamationDTO reclamationDTO = service.reclamer(email, matricule,sujet,date);
		if (reclamationDTO == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(reclamationDTO);
	}

//}

	@GetMapping("/afficher")
	public List<Reclamation>all(){
		return this.service.all();
	}
	@GetMapping("/afficherbyid")
	public Reclamation reclamatioyid(Long id) {
		return this.service.reclamatioyid(id);
	}
	@GetMapping("/afficherbyemail")
	public List<Reclamation> recbyemail(@RequestParam  String email ){
		return this.service.recbyemail(email);
	}
	@GetMapping("/afficherbydestinataire")
	public List<Reclamation> afficherbydestinatairel(@RequestParam  String email ){
		return this.service.findbydestinataire(email);
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
