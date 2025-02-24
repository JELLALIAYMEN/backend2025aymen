package com.DPC.spring.controllers;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.MenuRequest;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Menu;
import com.DPC.spring.repositories.MenuRepository;
import com.DPC.spring.services.IMenuService;

import javax.crypto.NoSuchPaddingException;

@CrossOrigin("*")
@RestController
@RequestMapping("menu")
public class MenuControler {
@Autowired
IMenuService service  ;
@Autowired
MenuRepository menurepos ;
	@Autowired
	MailService mail ;
	@PostMapping("/creer")
	public ResponseEntity<String> Creer(@RequestBody Menu m) {
		try {
			this.menurepos.save(m);
			return ResponseEntity.ok("Menu ajouté avec succès !");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de l'ajout du menu.");
		}
	}

	@GetMapping("/afficher")
	public List<Menu> afficher(){
		 return this.service.afficher();
	}
	@Autowired
	MailService mailservice ; 
	 @GetMapping("/envoyermail")
	    public ResponseEntity<String> envoyerEmail(String email) {
		 mailservice.envoyerEmailMenu(email);
	        return ResponseEntity.ok("E-mail envoyé avec succès !");
	    }
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
		// Recherche du menu existant par son ID
		Menu menu = menurepos.findById(id).orElse(null);

		// Vérification si le menu existe
		if (menu != null) {
			// Mise à jour du menu existant avec les nouvelles informations
			menu.setPlatprincipale(updatedMenu.getPlatprincipale());
			menu.setPlatentree(updatedMenu.getPlatentree());
			menu.setDessert(updatedMenu.getDessert());
			menu.setNomjour(updatedMenu.getNomjour());

			// Sauvegarde du menu mis à jour dans la base de données
			menurepos.save(menu);

			// Retourne une réponse indiquant que la mise à jour a été réussie
			return ResponseEntity.ok("Menu mis à jour avec succès !");
		} else {
			// Retourne une réponse indiquant que le menu n'a pas été trouvé
			return ResponseEntity.status(404).body("Menu non trouvé !");
		}
	}

	@PostMapping("/envoyer-menu")
	public ResponseEntity<String> envoyerMenu(@RequestBody MenuRequest request) {
		try {
			String response = service.Creermenu(
					new Menu(),
					request.getPlatEntree(),
					request.getPlatPrincipal(),
					request.getDessert(),
					request.getEmail(),
					request.getTypeMenu()
			);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de l'envoi du menu : " + e.getMessage());
		}
	}



}
