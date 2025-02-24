package com.DPC.spring.services;


import javax.mail.internet.MimeMessage;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

import com.DPC.spring.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.serviceImpl.IMenuServiceImpl;

/**
 * @author USER
 *
 */

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	IParentEleveService iParentEleveService;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	IPayementService IPaymentServiceImp;

	String emailClient = "tataouine@gmail.com";

	public void sendMailWithAttachment(String toEmail,
									   String body,
									   String subject,
									   String attchment) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(emailClient);
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);

		FileSystemResource fileSystemResource = new FileSystemResource(new File(attchment));
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
		javaMailSender.send(mimeMessage);
		System.out.println("Mail with attachment sent successfully.");


	}
@Autowired
IMenuServiceImpl menuService ;
	  public void envoyerEmailMenu(String destinataire) {
	        String sujet = "Menu de la semaine";
	        String contenuHTML = menuService.genererTableauHTML();

	        MimeMessage message = mailSender.createMimeMessage();
	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	            helper.setTo(destinataire);
	            helper.setSubject(sujet);
	            helper.setText(contenuHTML, true);
	            mailSender.send(message);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
	@Autowired
	UtilisateurRepository userrepos;

	public Map<String, Boolean> RenisialiserMotdepasse(String emailcrypter)
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Map<String, Boolean> success = new TreeMap<String, Boolean>();
		List<PasswordResetToken> listpasswordResetToken = new ArrayList<>();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			org.springframework.security.crypto.password.PasswordEncoder passwordEncorder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
			//Optional<Utilisateur> findByEmail()

			Utilisateur u = this.userrepos.findByEmail(emailcrypter);
			String email = encoder.encode(emailcrypter);
			String emails = email.replaceAll("/", "-");

			//String email = passwordEncorder.encode(emailcrypter);
			PasswordResetToken tosave = new PasswordResetToken();
			success.put("response", true);

			mimeMessageHelper.setSubject("test mail");
			mimeMessageHelper.setFrom(email);
			mimeMessageHelper.setTo(emailcrypter);
			String content = " Bonjour Mr (Mme), <br>"

					+ "Cordialement ,<br><br>";
			mimeMessageHelper.setText(content);
			// Add a resource as an attachment
			mimeMessageHelper.setText("<html><body><p>" + content
							+ "</p> </body></html>",
					true);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());


			success.put("response", false);
		} catch (MessagingException x) {
			x.printStackTrace();
		}
		return success;

	}


	public Map<String, Boolean> EnvoyerEmploi(String emailcrypter, Date date, String heure, String jour, String matiere, String salle, String prof)
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Map<String, Boolean> success = new TreeMap<String, Boolean>();
		List<PasswordResetToken> listpasswordResetToken = new ArrayList<>();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			org.springframework.security.crypto.password.PasswordEncoder passwordEncorder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
			Utilisateur u = this.userrepos.findByEmail(emailcrypter);
			String email = encoder.encode(emailcrypter);
			String emails = email.replaceAll("/", "-");
			PasswordResetToken tosave = new PasswordResetToken();
			success.put("response", true);

			mimeMessageHelper.setSubject("Emploi du temps");
			mimeMessageHelper.setFrom(email);
			mimeMessageHelper.setTo(emailcrypter);
			String content = " Bonjour Mr (Mme), " + u.getNom() + " " + u.getPrenom() + " Voila la nouvelle emploie <br>"
					+ "Date : " + date + " ,<br><br>"
					+ "heure :" + heure + " ,<br><br>"
					+ "Jour:" + jour + ",<br><br>"
					+ "Matiere :" + matiere + " ,<br><br>"
					+ "Salle:" + salle + ",<br><br>"
					+ "Prof:" + prof + ",<br><br>"

					+ "Cordialement ,<br><br>";
			mimeMessageHelper.setText(content);
			// Add a resource as an attachment
			mimeMessageHelper.setText("<html><body><p>" + content
							+ "</p> </body></html>",
					true);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());


			success.put("response", false);
		} catch (MessagingException x) {
			x.printStackTrace();
		}
		return success;

	}

	public Map<String, Boolean> calendrierExamen(String emailcrypter, Date date, String heure, String jour, String matiere, String salle, String prof, String type)
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Map<String, Boolean> success = new TreeMap<String, Boolean>();
		List<PasswordResetToken> listpasswordResetToken = new ArrayList<>();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			org.springframework.security.crypto.password.PasswordEncoder passwordEncorder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
			Utilisateur u = this.userrepos.findByEmail(emailcrypter);
			String email = encoder.encode(emailcrypter);
			String emails = email.replaceAll("/", "-");
			PasswordResetToken tosave = new PasswordResetToken();
			success.put("response", true);

			mimeMessageHelper.setSubject("Calendrier Examen");
			mimeMessageHelper.setFrom(email);
			mimeMessageHelper.setTo(emailcrypter);
			String content = " Bonjour Mr (Mme), " + u.getNom() + " " + u.getPrenom() + " Voila la nouvelle emploie <br>"
					+ "Date : " + date + " ,<br><br>"
					+ "heure :" + heure + " ,<br><br>"
					+ "Jour:" + jour + ",<br><br>"
					+ "Matiere :" + matiere + " ,<br><br>"
					+ "Salle:" + salle + ",<br><br>"
					+ "Prof:" + prof + ",<br><br>"
					+ "Type Calendrier:" + type + ",<br><br>"

					+ "Cordialement ,<br><br> Scolarite";
			mimeMessageHelper.setText(content);
			// Add a resource as an attachment
			mimeMessageHelper.setText("<html><body><p>" + content
							+ "</p> </body></html>",
					true);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());


			success.put("response", false);
		} catch (MessagingException x) {
			x.printStackTrace();
		}
		return success;

	}


	public Map<String, Boolean> envoyerCalendrier(String emailDestinataire, List<Calendrierexamen> calendrierList, Trimestre t, String typecalendrier) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Map<String, Boolean> success = new TreeMap<>();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			// Grouper par java.util.Date et trier automatiquement
			Map<Date, List<Calendrierexamen>> calendrierParDate = calendrierList.stream()
					.collect(Collectors.groupingBy(
							Calendrierexamen::getDate,
							() -> new TreeMap<>(),
							Collectors.toList()
					));

			Utilisateur u = this.userrepos.findByEmail(emailDestinataire);

			// Construire le tableau HTML
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append("Bonjour,<br><br> Cher Mr/Mme : " + u.getNom() + " " + u.getPrenom() + " <br>" +
							"Voici le calendrier des devoirs de " + " " + typecalendrier + " de " + " " + t)
					.append("<h1 align='center'>" + u.getClasse().getNomclasse() + "</h1>")
					.append("<table border='1' style='border-collapse: collapse; width: 100%; text-align: left;'>");

			// Ligne des jours avec dates
			contentBuilder.append("<tr>");
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy", Locale.FRENCH);
			for (Date date : calendrierParDate.keySet()) {
				String jourAvecDate = dateFormat.format(date); // Formatage pour afficher jour et date
				contentBuilder.append("<th>").append(jourAvecDate.replace(" ", "<br>")).append("</th>");
			}
			contentBuilder.append("</tr>");

			// Trouver toutes les périodes uniques pour les lignes
			List<String> toutesPeriodes = calendrierList.stream()
					.map(Calendrierexamen::getPeriode)
					.distinct()
					.sorted()
					.collect(Collectors.toList());

			// Construire les lignes pour chaque période
			for (String periode : toutesPeriodes) {
				contentBuilder.append("<tr>");
				for (Date date : calendrierParDate.keySet()) {
					List<Calendrierexamen> elementsDuJour = calendrierParDate.getOrDefault(date, new ArrayList<>()).stream()
							.filter(cal -> cal.getPeriode().equals(periode))
							.collect(Collectors.toList());

					String contenu = elementsDuJour.stream()
							.map(cal -> cal.getMatiere().getNom() + " " + cal.getSalle().getNomdesalle() + "<br>(" + cal.getPeriode() + ")")
							.collect(Collectors.joining("<br>"));

					contentBuilder.append("<td>").append(contenu.isEmpty() ? " - " : contenu).append("</td>");
				}
				contentBuilder.append("</tr>");
			}

			contentBuilder.append("</table>")
					.append("<br>Cordialement,<br>Votre équipe.");

			// Configurer l'email
			mimeMessageHelper.setSubject("Calendrier");
			mimeMessageHelper.setFrom("votre.adresse@email.com"); // Remplacez par votre email
			mimeMessageHelper.setTo(emailDestinataire);
			mimeMessageHelper.setText(contentBuilder.toString(), true);

			// Envoyer l'email
			javaMailSender.send(mimeMessage);

			success.put("response", true);
		} catch (MessagingException e) {
			e.printStackTrace();
			success.put("response", false);
		}

		return success;
	}

	public Map<String, Boolean> envoyerNotificationDisciplineAleleve(String eleveEmail, String adminEmail, String cause, String adminComment) {
		Map<String, Boolean> success = new TreeMap<>();

		try {
			// Créer et envoyer l'email de notification à l'élève
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("Notification Discipline");
			mimeMessageHelper.setFrom(adminEmail);
			mimeMessageHelper.setTo(eleveEmail); // Envoi à l'élève

			String content = "Bonjour,\n\n"
					+ "Voici les détails concernant votre discipline :\n"
					+ cause + "\n\n"
					+ "Commentaire de l'admin : " + adminComment + "\n\n"
					+ "Cordialement.";

			mimeMessageHelper.setText(content);

			// Envoi de l'email
			javaMailSender.send(mimeMessage);
			success.put("response", true); // Indique le succès de l'envoi
		} catch (MessagingException e) {
			e.printStackTrace();
			success.put("response", false); // Indique un échec de l'envoi
		} catch (Exception e) {
			e.printStackTrace();
			success.put("response", false); // Indique un échec de l'envoi
		}

		return success;
	}

	
	
	public Map<String, Boolean> envoyertravaillehomeword(String email) {
		Map<String, Boolean> success = new TreeMap<>();

		try {
			Utilisateur u = this.userrepos.findByEmail(email);
			// Créer et envoyer l'email de notification à l'élève
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("Traveille HomeWork");
			mimeMessageHelper.setFrom(email);
			mimeMessageHelper.setTo(email); // Envoi à l'élève

			String content = "Bonjour, Mr(s) "+u.getNom()+" "+u.getPrenom()+"\n\n"
					+ "Veuillez Consulter Votre Esapce \n"
					+ "Cordialement.";

			mimeMessageHelper.setText(content);

			// Envoi de l'email
			javaMailSender.send(mimeMessage);
			success.put("response", true); // Indique le succès de l'envoi
		} catch (MessagingException e) {
			e.printStackTrace();
			success.put("response", false); // Indique un échec de l'envoi
		} catch (Exception e) {
			e.printStackTrace();
			success.put("response", false); // Indique un échec de l'envoi
		}

		return success;
	}

	
	public void envoyerNotificationDisciplineToParent(Utilisateur eleve, String parentEmail, String cause, LocalDate date, String adminEmail, String adminComment) {
		try {
			// Créer et envoyer l'email au parent
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("Notification Discipline de votre enfant");
			mimeMessageHelper.setFrom(adminEmail);
			mimeMessageHelper.setTo(parentEmail); // Envoi au parent

			String content = "Bonjour, \n\nVoici les détails concernant la discipline de votre enfant "
					+ eleve.getNom() + " " + eleve.getPrenom() + " : \n\n"
					+ "Cause : " + cause + "\n"
					+ "Commentaire de l'admin : " + adminComment + "\n"
					+ "Date : " + date + "\n\nCordialement.";
			mimeMessageHelper.setText(content);

			// Envoi de l'email
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Log de l'erreur ou gestion de l'exception
		}
	}

	public void envoyerNotificationDisciplineAEneseignant(Utilisateur eleve, String mailEnseignant, String adminComment) {
		try {
			// Créer et envoyer l'email au parent
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("Reject de validation de discipline");
			mimeMessageHelper.setFrom("mail");
			mimeMessageHelper.setTo(mailEnseignant); // Envoi au parent

			String content = "Bonjour, \n\nLa demande de valider votre discipline pour l'élève "
					+ eleve.getNom() + " " + eleve.getPrenom() + " \n\n"
					+ "est rejetée pour cette raison: " + adminComment;
			mimeMessageHelper.setText(content);

			// Envoi de l'email
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			// Log de l'erreur ou gestion de l'exception
		}
	}

	public void sendEmailToAdmin(Discipline discipline, String adminMail, String enseignantMail) {
		// Logique pour envoyer l'e-mail à l'admin
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("Discipline en attente d'approbation");
			mimeMessageHelper.setFrom(enseignantMail);
			mimeMessageHelper.setTo(adminMail); // Remplacez par l'email de l'admin

			String content = "Bonjour,\n\nUne discipline a été mise en attente d'approbation.\n\n"
					+ "Détails de la discipline :\n"
					+ "Élève : " + discipline.getEleve().getNom() + " " + discipline.getEleve().getPrenom() + "\n"
					+ "Cause : " + discipline.getCause() + "\n"
					+ "Date : " + discipline.getDate() + "\n\nCordialement.";

			mimeMessageHelper.setText(content);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace(); // Log ou gestion de l'exception
		}
	}

	public void sendMail(String to, String subject, String text) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		javaMailSender.send(mimeMessage);
	}

	public void sendSimpleMessage(String email, String subject, String message) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(email);
			mailMessage.setSubject(subject);
			mailMessage.setText(message);
			javaMailSender.send(mailMessage);
			System.out.println("Notification envoyée avec succès à " + email);
		} catch (Exception e) {
			System.err.println("Erreur lors de l'envoi de la notification : " + e.getMessage());
		}
	}

	public Map<String, Boolean> emploie(String emailDestinataire, List<Emploidetemps> emploiList) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Map<String, Boolean> success = new TreeMap<>();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			List<String> joursOrdre = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi");
			Utilisateur u = this.userrepos.findByEmail(emailDestinataire);
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append("Bonjour, <br><br> Cher Mr/Mme " + u.getNom() + " " + u.getPrenom() + " de la classe " + u.getClasse().getNomclasse() + " <br><br>")
					.append("Voici l'emploi de temps pour l'année scolaire (2024/2025) :<br><br>")
					.append("<table border='1' style='border-collapse: collapse; width: 100%; text-align: center;'>")
					.append("<thead>")
					.append("<tr>")
					.append("<th>Heures</th>");

			for (String jour : joursOrdre) {
				contentBuilder.append("<th>").append(jour).append("</th>");
			}
			contentBuilder.append("</tr>")
					.append("</thead>")
					.append("<tbody>");

			List<String> heures = emploiList.stream()
					.map(Emploidetemps::getHeure)
					.distinct()
					.sorted() // Tri des heures
					.collect(Collectors.toList());

			for (String heure : heures) {
				contentBuilder.append("<tr>")
						.append("<td>").append(heure).append("</td>"); // Ajouter l'heure dans la première colonne

				for (String jour : joursOrdre) {
					// Filtrer les emplois pour le jour et l'heure actuels
					List<Emploidetemps> emplois = emploiList.stream()
							.filter(e -> e.getNomjour().equalsIgnoreCase(jour) && e.getHeure().equals(heure))
							.collect(Collectors.toList());

					if (emplois.isEmpty()) {
						contentBuilder.append("<td></td>"); // Ajouter une cellule vide si aucun emploi
					} else {
						// Combiner les matières et les salles
						String contenuCellule = emplois.stream()
								.map(e -> e.getMatiere().getNom() + " (" + e.getSalle().getNomdesalle() + ")")
								.collect(Collectors.joining("<hr>"));
						contentBuilder.append("<td>").append(contenuCellule).append("</td>");
					}
				}
				contentBuilder.append("</tr>");
			}

			contentBuilder.append("</tbody>")
					.append("</table>")
					.append("<br><br>Cordialement,<br>Scolarité.");

			// Configurer l'email
			mimeMessageHelper.setSubject("Emploi du temps");
			mimeMessageHelper.setFrom("votre.adresse@email.com"); // Remplacez par votre adresse email
			mimeMessageHelper.setTo(emailDestinataire);

			// Ajouter le contenu HTML
			mimeMessageHelper.setText(contentBuilder.toString(), true);

			// Envoyer l'email
			javaMailSender.send(mimeMessage);

			success.put("response", true);
		} catch (MessagingException e) {
			e.printStackTrace();
			success.put("response", false);
		}
		return success;
	}

	public Map<String, Object> EnvoyerPayement(String emailcrypter, String matricule, Double montantpay, Typepay typepay, Date date)
			throws NoSuchAlgorithmException, NoSuchPaddingException {

		Map<String, Object> response = new HashMap<>();
		List<Map<String, Object>> paiementDetails = new ArrayList<>();

		try {
			// Récupérer l'utilisateur
			Utilisateur u = userrepos.findByEmail(emailcrypter);
			if (u==null) {
				response.put("success", false);
				response.put("message", "Utilisateur introuvable pour l'email fourni.");
				return response;
			}

			// Créer l'objet MimeMessage pour l'envoi de l'email
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			// Définir le sujet et les informations de l'email
			mimeMessageHelper.setSubject("Confirmation de paiement");
			mimeMessageHelper.setFrom("votre-email@example.com"); // Remplacez par un email valide
			mimeMessageHelper.setTo(emailcrypter);

			// Enregistrer les informations sous forme de tableau
			Map<String, Object> paiementInfo = new HashMap<>();
			paiementInfo.put("Nom", u.getNom());
			paiementInfo.put("Prénom", u.getPrenom());
			paiementInfo.put("Matricule", matricule);
			paiementInfo.put("Type de paiement", typepay);
			paiementInfo.put("Montant payé", montantpay);
			paiementInfo.put("Date", date.toString());

			// Ajouter les données à la liste
			paiementDetails.add(paiementInfo);

			// Construire le contenu HTML de l'email
			String content = "<html><body>"
					+ "<h2>Confirmation de paiement</h2>"
					+ "<p>Bonjour Mr/Mme <strong>" + u.getNom() + " " + u.getPrenom() + "</strong>,</p>"
					+ "<p>Voici les détails de votre paiement :</p>"
					+ "<table border='1' cellpadding='5' cellspacing='0'>"
					+ "<tr><th>Matricule</th><th>Type de paiement</th><th>Montant</th><th>Date</th></tr>"
					+ "<tr><td>" + matricule + "</td><td>" + typepay + "</td><td>" + montantpay + " TND</td><td>" + date + "</td></tr>"
					+ "</table>"
					+ "<br><p>Cordialement,<br><strong>Votre établissement</strong></p>"
					+ "</body></html>";

			mimeMessageHelper.setText(content, true);
			javaMailSender.send(mimeMessage);

			// Stocker la liste dans la réponse
			response.put("success", true);
			response.put("paiements", paiementDetails);
		} catch (MessagingException e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("message", "Erreur lors de l'envoi de l'email.");
		}
		return response;
	}

	public Map<String, Object> envoyerNotificationCredit(String emailCrypte, String matricule) {
		Map<String, Object> response = new HashMap<>();

		// Récupérer l'utilisateur
		Utilisateur utilisateur = utilisateurRepository.findByEmail(emailCrypte);
		if (utilisateur==null) {
			response.put("success", false);
			response.put("message", "Utilisateur introuvable pour l'email fourni.");
			return response;
		}

		// Calculer le crédit total
		Double creditTotal = IPaymentServiceImp.calculerCreditTotal(matricule);

		// Créer et envoyer l'e-mail
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setSubject("Votre solde de crédit");
			helper.setFrom("votre-email@example.com"); // Remplacez par un e-mail valide
			helper.setTo(emailCrypte);

			String contenu = "<html><body>"
					+ "<h2>Bonjour " + utilisateur.getPrenom() + " " + utilisateur.getNom() + ",</h2>"
					+ "<p>Nous souhaitons vous informer que votre solde de crédit actuel est de : <strong>"
					+ creditTotal + " TND</strong>.</p>"
					+ "<p>Merci de votre confiance.</p>"
					+ "<br><p>Cordialement,<br><strong>Votre établissement</strong></p>"
					+ "</body></html>";

			helper.setText(contenu, true);
			javaMailSender.send(mimeMessage);

			response.put("success", true);
			response.put("message", "E-mail de notification envoyé avec succès.");
		} catch (MessagingException e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("message", "Erreur lors de l'envoi de l'e-mail.");
		}

		return response;
	}



	public Map<Typepay, Double> calculerCreditsParTypepay(String matricule) {
		// Récupérer l'utilisateur par matricule
		Utilisateur utilisateur = utilisateurRepository.findByMatricule(matricule);

		if (utilisateur == null) {
			throw new IllegalArgumentException("Aucun utilisateur trouvé pour le matricule : " + matricule);
		}

		// Montant à payer
		Double montantAPayer = utilisateur.getMontantAnnuel();

		if (montantAPayer == null) {
			throw new IllegalStateException("Le montant à payer n'est pas défini pour l'utilisateur.");
		}

		// Initialiser la map pour les crédits
		Map<Typepay, Double> creditsParTypepay = new HashMap<>();

		for (Typepay typepay : Typepay.values()) {
			List<Payement> paiements = utilisateur.getPayements().stream()
					.filter(p -> p.getTypepay() == typepay)
					.collect(Collectors.toList());

			Double montantTotalPayé = paiements.stream()
					.mapToDouble(Payement::getMontantpay)
					.sum();

			Double credit = montantAPayer - montantTotalPayé;

			creditsParTypepay.put(typepay, credit);
		}

		return creditsParTypepay;
	}

	public void envoyerEmail(String emailDestinataire, String classe) {
		List<Utilisateur> utilisateurs = utilisateurRepository.findByClasseNomclasse(classe);
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			// Construction du contenu HTML
			StringBuilder contentBuilder = new StringBuilder();
			contentBuilder.append("<html><body>");
			contentBuilder.append("<h3>Tableau des Crédits et Paiements</h3>");
			contentBuilder.append("<table border='1' style='border-collapse: collapse; width: 100%; text-align: center;'>");
			contentBuilder.append("<thead><tr><th>Matricule</th>");

			// Ajouter les entêtes des types de paiement
			for (Typepay type : Typepay.values()) {
				contentBuilder.append("<th>").append(type).append("</th>");
			}
			contentBuilder.append("</tr></thead>");
			contentBuilder.append("<tbody>");

			// Ajouter les informations des utilisateurs et leurs crédits
			for (Utilisateur utilisateur : utilisateurs) {
				contentBuilder.append("<tr>");
				contentBuilder.append("<td>").append(utilisateur.getMatricule()).append("</td>");

				// Calculer les crédits par type de paiement
				Map<Typepay, Double> credits = calculerCreditsParTypepay(utilisateur.getMatricule());

				for (Typepay typepay : Typepay.values()) {
					contentBuilder.append("<td>").append(credits.getOrDefault(typepay, 0.0)).append("</td>");
				}
				contentBuilder.append("</tr>");
			}

			contentBuilder.append("</tbody>");
			contentBuilder.append("</table>");
			contentBuilder.append("</body></html>");

			// Configuration de l'e-mail
			mimeMessageHelper.setSubject("Détails des paiements et crédits");
			mimeMessageHelper.setFrom("votre.adresse@email.com"); // Remplacez par votre adresse e-mail
			mimeMessageHelper.setTo(emailDestinataire);

			// Ajout du contenu HTML
			mimeMessageHelper.setText(contentBuilder.toString(), true);

			// Envoi de l'e-mail
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public Map<String, Boolean> EnvoyerMenu(String email, String platEntree, String platPrincipal, String dessert, TypeMenu typeMenu)
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Map<String, Boolean> success = new TreeMap<>();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			// Récupération de l'utilisateur
			Utilisateur u = this.utilisateurRepository.findByEmail(email);

			if (u==null) {
				success.put("response", false);
				return success;
			}

			mimeMessageHelper.setSubject("Nouveau Menu Disponible");
			mimeMessageHelper.setFrom("tonemail@example.com");  // Remplace par ton adresse
			mimeMessageHelper.setTo(email);

			// Contenu de l'email
			String content = "<html><body>" +
					"<p>Bonjour " + u.getNom() + " " + u.getPrenom() + ",</p>" +
					"<p>Voici le menu du jour :</p>" +
					"<ul>" +
					"<li><strong>Type de menu :</strong> " + typeMenu + "</li>" +
					"<li><strong>Entrée :</strong> " + platEntree + "</li>" +
					"<li><strong>Plat principal :</strong> " + platPrincipal + "</li>" +
					"<li><strong>Dessert :</strong> " + dessert + "</li>" +
					"</ul>" +
					"<p>Cordialement,</p>" +
					"</body></html>";

			mimeMessageHelper.setText(content, true);

			// Envoi de l'email
			javaMailSender.send(mimeMessageHelper.getMimeMessage());

			success.put("response", true);
		} catch (MessagingException e) {
			e.printStackTrace();
			success.put("response", false);
		}

		return success;
	}
	public void sendEmail(String to, String subject, String body) {
		try {
			// Création d'un message simple
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);

			// Envoi de l'email
			mailSender.send(message);
			System.out.println("Email envoyé à : " + to);
		} catch (MailException e) {
			System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
		}
	}


}

