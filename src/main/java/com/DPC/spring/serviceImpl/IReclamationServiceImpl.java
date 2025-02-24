package com.DPC.spring.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.DPC.spring.DTO.ReclamationDTO;
import com.DPC.spring.Mapper.Mapperdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.DPC.spring.entities.Reclamation;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ReclamationRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.IReclamationService;

import javax.persistence.EntityNotFoundException;

@Service
public class IReclamationServiceImpl implements IReclamationService {
	@Autowired
	ReclamationRepository reclamationrepos;
	@Autowired
	Mapperdto mapperdto;
	@Autowired
	UtilisateurRepository userrepos;
	@Autowired
	private JavaMailSender mailSender;
	@Override
	public ReclamationDTO reclamer(String email, String matricule, String sujet, Date date) {
		// Récupérer l'utilisateur (celui qui envoie la réclamation) à partir de son matricule
		Utilisateur reclamant = userrepos.findByMatricule(matricule);
		if (reclamant == null) {
			// Vous pouvez gérer cette erreur plus explicitement
			throw new RuntimeException("Utilisateur non trouvé pour le matricule: " + matricule);
		}

		// Vérifier si le destinataire (enseignant) existe et a le profil 'Teacher'
		Utilisateur destinataire = userrepos.findByEmail(email);
		if (destinataire==null) {
			// Gérer également cette erreur avec un message explicite
			throw new RuntimeException("Destinataire non trouvé pour l'email: " + email);
		}

		// Créer la réclamation
		Reclamation r = new Reclamation();
		r.setUser(reclamant); // L'utilisateur qui envoie la réclamation
		r.setDestinataire(destinataire); // L'enseignant qui reçoit la réclamation
		r.setResultat("Non Traité");
		r.setDate(date);// Statut initial de la réclamation

		// Attribuer le sujet dynamique à la réclamation
		r.setSujet(sujet); // Le sujet envoyé par l'utilisateur

		// Enregistrer la réclamation dans la base de données
		Reclamation savedReclamation = reclamationrepos.save(r);

		// Envoyer la réclamation par email
		sendEmailToDestinataire(destinataire.getEmail(), r);

		// Retourner le DTO de la réclamation enregistrée
		return mapperdto.fromReclamation(savedReclamation);
	}

	private void sendEmailToDestinataire(String destinataireEmail, Reclamation r) {
		// Préparer l'email
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(destinataireEmail);
		message.setSubject("Nouvelle réclamation de " + r.getUser().getNom()); // Ajouter le nom de l'utilisateur pour plus de personnalisation
		message.setText("Vous avez reçu une nouvelle réclamation de " + r.getUser().getNom() + ".\n\n"
				+ "Sujet : " + r.getSujet() + "\n"
				+ "Détails : " + r.getResultat() + "\n"
				+ "Date de la réclamation : " + r.getDate()); // Ajouter la date si nécessaire

		// Envoyer l'email
		try {
			mailSender.send(message);
		} catch (MailException e) {
			// Gérer l'erreur d'envoi d'email
			System.err.println("Erreur lors de l'envoi de l'email à " + destinataireEmail);
			e.printStackTrace();
		}
	}


	public List<Reclamation>all(){
		return this.reclamationrepos.findAll();
	}
	public Reclamation reclamatioyid(Long id) {
		Reclamation r = this.reclamationrepos.findById(id).get();
		return r ;
	}
	public List<Reclamation> recbyemail(String email) {
		Utilisateur u = this.userrepos.findByEmail(email);
		if (u==null) {
			throw new EntityNotFoundException("Utilisateur non trouvé avec l'email : " + email);
		}
		return this.reclamationrepos.findByUserOrderByDateDesc(u);
	}


	@Override
	public List<Reclamation> findbydestinataire(String email) {
	Utilisateur u = userrepos.findByEmail(email);
		List<Reclamation> listbydestinataire = this.reclamationrepos.findByDestinataireOrderByDateDesc(u);
		return listbydestinataire;
	}

	public String reponse(Long id, String reponse) {
		Reclamation r = this.reclamationrepos.findById(id).get();
		r.setResultat(reponse);
		this.reclamationrepos.saveAndFlush(r);
		return "true" ;

	}

	public String supprimer(Long id) {
		Reclamation r = this.reclamationrepos.findById(id).get();
		this.reclamationrepos.delete(r);
		return "true" ;
	}
} 
