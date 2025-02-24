package com.DPC.spring.serviceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Departement;
import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Nondisponible;
import com.DPC.spring.entities.Salle;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.repositories.EmploiRepository;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.repositories.NomdispoRepository;
import com.DPC.spring.repositories.SalleRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.IEmploiService;
import com.DPC.spring.services.MailService;

@Service
public class EmploiServiceImpl implements IEmploiService {
@Autowired
UtilisateurRepository userepos ;
@Autowired
EmploiRepository emploirepos ; 

@Autowired
DepartementRepository deparrepos ; 
@Autowired
Matiererep matrepos ; 
@Autowired
ClasseRepository classeRepos ; 
@Autowired
NomdispoRepository nondispo ; 
@Autowired
SalleRepository sallerepos ; 
@Autowired
MailService mailservice ;

	public String Creeremploi(Emploidetemps e, String email, String salles, String matiere, String classe) throws NoSuchAlgorithmException, NoSuchPaddingException {
		Utilisateur u = this.userepos.findByEmail(email);
		if (u==null) {
			return "Utilisateur non trouvé";
		}
		Utilisateur p = u;

		Salle s = this.sallerepos.findByNomdesalle(salles);
		if (s == null) {
			return "Salle non trouvée";
		}

		Matiere m = this.matrepos.findByNom(matiere);
		if (m == null) {
			return "Matière non trouvée";
		}

		Classe c = this.classeRepos.findByNomclasse(classe);
		if (c == null) {
			return "Classe non trouvée";
		}

		List<Utilisateur> list = this.userepos.findByClasseNomclasse(c.getNomclasse());

		// Vérification si l'utilisateur est déjà indisponible ce jour-là
		Nondisponible existe = this.nondispo.findByNomjourAndUser(e.getNomjour(), p);
		if (existe != null) {
			return "Le professeur n'est pas disponible ce jour-là";
		}

		// Vérification si le professeur a déjà un emploi du temps pour cette classe ce jour-là
		Emploidetemps emploiprofclasse = this.emploirepos.findByUserAndClasseAndNomjour(p, c, e.getNomjour());
		if (emploiprofclasse != null) {
			return "Le professeur a déjà un cours pour cette classe aujourd'hui";
		}

		// Vérification si la classe est déjà occupée à cette heure
		Emploidetemps emploiclasse = this.emploirepos.findByClasseAndNomjourAndHeure(c, e.getNomjour(), e.getHeure());
		if (emploiclasse != null) {
			return "Cette classe est déjà occupée à cette heure";
		}

		// Vérification si la salle est disponible
		Emploidetemps esalle = this.emploirepos.findBySalleAndNomjourAndHeure(s, e.getNomjour(), e.getHeure());
		if (esalle != null) {
			return "La salle n'est pas disponible à cette heure";
		}

		// Si toutes les vérifications sont passées, on enregistre l'emploi du temps
		e.setClasse(c);
		e.setMatiere(m);
		e.setSalle(s);
		e.setUser(p);
		this.emploirepos.save(e);

		// Envoi des informations par mail à tous les utilisateurs de la classe
		for (Utilisateur utilisateur : list) {
			this.mailservice.EnvoyerEmploi(utilisateur.getEmail(), e.getDate(), e.getHeure(), e.getNomjour(), matiere, salles, p.getNom());
		}

		return "Emploi du temps créé avec succès";
	}



		
	
		
	}
	

	
	
		
	

