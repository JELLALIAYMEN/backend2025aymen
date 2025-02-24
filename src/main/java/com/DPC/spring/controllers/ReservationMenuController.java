package com.DPC.spring.controllers;

import com.DPC.spring.entities.Menu;
import com.DPC.spring.entities.Reservationmenu;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.MenuRepository;
import com.DPC.spring.repositories.ReservationMenuRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationMenuController {

	@Autowired
	private MenuRepository menurepos;

	@Autowired
	private UtilisateurRepository userrepos;

	@Autowired
	private ReservationMenuRepository reservationrepos;

	// Créer une réservation
	@PostMapping("/creer")
	public ResponseEntity<String> creer(@RequestParam Long idmenu, @RequestParam String email) {
		// Vérifier si le menu existe
		Optional<Menu> menuOptional = this.menurepos.findById(idmenu);
		if (!menuOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu non trouvé");
		}

		// Vérifier si l'utilisateur existe
		Utilisateur utilisateurOptional = this.userrepos.findByEmail(email);
		if (utilisateurOptional==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
		}

		// Créer la réservation
		Menu m = menuOptional.get();
		Utilisateur u = utilisateurOptional;

		Reservationmenu reservation = new Reservationmenu();
		reservation.setEleve(u);
		reservation.setMenu(m);

		// Sauvegarder la réservation
		this.reservationrepos.save(reservation);

		// Réponse de succès
		return ResponseEntity.status(HttpStatus.CREATED).body("Réservation créée avec succès");
	}

	// Afficher toutes les réservations
	@GetMapping("/afficher")
	public ResponseEntity<List<Reservationmenu>> all() {
		List<Reservationmenu> reservations = this.reservationrepos.findAll();
		return ResponseEntity.ok(reservations);
	}

	// Afficher les réservations par email de l'utilisateur
	@GetMapping("/afficherbyemail")
	public ResponseEntity<List<Reservationmenu>> allByEmail(@RequestParam String email) {
		Utilisateur u = this.userrepos.findByEmail(email);
		if (u==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		List<Reservationmenu> reservations = this.reservationrepos.findByEleve(u);
		return ResponseEntity.ok(reservations);
	}
}

