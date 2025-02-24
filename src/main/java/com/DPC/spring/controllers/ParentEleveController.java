package com.DPC.spring.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.DPC.spring.entities.Module;
import com.DPC.spring.services.IParentEleveService;
import com.DPC.spring.services.Moduleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.ParentEleve;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ParentElveRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("parent")
public class ParentEleveController {
@Autowired
ParentElveRepository perepos ; 
@Autowired
UtilisateurRepository userrepos ;
	@Autowired
	IParentEleveService ParentEleveServiceImp;
	@Autowired
	private Moduleservice moduleservice ;
	@PostMapping("/affecter/{emailparent}/{emaileleve}")
	public String affecter(@PathVariable String emailparent, @PathVariable String emaileleve) {
		// Recherche de l'élève et du parent par leurs emails
		Utilisateur ueOpt = this.userrepos.findByEmail(emaileleve);
		Utilisateur upOpt = this.userrepos.findByEmail(emailparent);

		// Vérification si l'élève et le parent existent
		if (ueOpt==null) {
			return "Élève non trouvé avec l'email : " + emaileleve;
		}
		if (upOpt==null) {
			return "Parent non trouvé avec l'email : " + emailparent;
		}

		Utilisateur ue = ueOpt;
		Utilisateur up = upOpt;

		// Création de la relation Parent-Eleve
		ParentEleve PE = new ParentEleve();
		PE.setEleve(ue);
		PE.setParent(up);

		// Sauvegarde dans la base de données
		this.perepos.save(PE);

		return "Relation Parent-Élève créée avec succès";
	}

	@GetMapping("/affichermeseleve/{emailparent}")
	public List<ParentEleve> affichermeseleve(@PathVariable String emailparent) {
		// Recherche du parent par son email
		Utilisateur upOpt = this.userrepos.findByEmail(emailparent);

		// Vérification si le parent existe
		if (upOpt==null) {
			return new ArrayList<>(); // Retourne une liste vide si le parent n'est pas trouvé
		}

		Utilisateur up = upOpt;
		return this.perepos.findByParent(up);
	}

	@PostMapping("/affecterTravail/{emailParent}")
	public ResponseEntity<String> affecterTravail(@PathVariable String emailParent, @RequestBody String travailDescription) {
		// Appel du service pour affecter le travail
		return ParentEleveServiceImp.affecterTravail(emailParent, travailDescription);
	}

	// Endpoint pour récupérer les travaux affectés à un parent
	@GetMapping("/afficherTravail/{emailParent}")
	public ResponseEntity<List<Module>> afficherTravail(@PathVariable String emailParent) {
		// Appel du service pour récupérer les travaux
		List<Module> travaux = ParentEleveServiceImp.getTravauxParParent(emailParent);
		return ResponseEntity.ok(travaux);
	}

}
