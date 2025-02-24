package com.DPC.spring.serviceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;

import com.DPC.spring.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DPC.spring.repositories.CalendrierExamenReposository;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.repositories.NomdispoRepository;
import com.DPC.spring.repositories.SalleRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.ICalendrierExamenService;
import com.DPC.spring.services.MailService;

@Service
public class CalendrierExamenImpl implements ICalendrierExamenService {

	@Autowired
	UtilisateurRepository userepos;

	@Autowired
	CalendrierExamenReposository calendrierrepos;

	@Autowired
	DepartementRepository deparrepos;

	@Autowired
	Matiererep matrepos;

	@Autowired
	ClasseRepository classeRepos;

	@Autowired
	NomdispoRepository nondispo;

	@Autowired
	SalleRepository sallerepos;

	@Autowired
	MailService mailservice;

	public String Creercalendrier(Calendrierexamen calendrier, String email, String salles, String matiere, String classe, String typecalendrier, Trimestre t) throws NoSuchAlgorithmException, NoSuchPaddingException {

		Utilisateur p = this.userepos.findByEmail(email);

		if (p==null) {
			return "Utilisateur non trouvé";
		}

		if (!p.getAuthority().getName().equals("enseignant")) {
			return "Accès interdit : utilisateur non enseignant";
		}

		Salle s = this.sallerepos.findByNomdesalle(salles);
		Matiere m = this.matrepos.findByNom(matiere);
		Classe c = this.classeRepos.findByNomclasse(classe);
		List<Utilisateur> list = this.userepos.findByClasseNomclasse(c.getNomclasse());

		if (typecalendrier.equals("synthese")) {

			Calendrierexamen existingExamen = this.calendrierrepos.findByClasseAndNomjourAndPeriode(c, calendrier.getNomjour(), calendrier.getPeriode());
			if (existingExamen != null) {
				return "Cette classe a déjà un examen pour cette période";
			}

			Calendrierexamen existingUserExamen = this.calendrierrepos.findByUserAndNomjourAndPeriode(p, calendrier.getNomjour(), calendrier.getPeriode());
			if (existingUserExamen != null) {
				return "Ce professeur a déjà un examen pour cette période";
			}

			Calendrierexamen existingSalleExamen = this.calendrierrepos.findBySalleAndNomjourAndPeriode(s, calendrier.getNomjour(), calendrier.getPeriode());
			if (existingSalleExamen != null) {
				return "La salle n'est pas disponible pour cette période";
			}

			// Création du calendrier
			calendrier.setClasse(c);
			calendrier.setMatiere(m);
			calendrier.setSalle(s);
			calendrier.setTrimestre(t);
			calendrier.setUser(p);
			calendrier.setTypecalendrier("Synthèse");
			this.calendrierrepos.save(calendrier);

		} else {
			calendrier.setClasse(c);
			calendrier.setMatiere(m);
			calendrier.setSalle(s);
			calendrier.setUser(p);
			calendrier.setTrimestre(t);
			calendrier.setTypecalendrier("Contrôle");
			this.calendrierrepos.save(calendrier);
		}

		// Envoi des emails
		for (Utilisateur user : list) {
			this.mailservice.calendrierExamen(user.getEmail(), calendrier.getDate(), calendrier.getPeriode(), calendrier.getNomjour(), matiere, salles, p.getNom(), typecalendrier);
		}

		return "Calendrier créé avec succès";
	}

}

