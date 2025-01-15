package com.DPC.spring.services;


import javax.mail.internet.MimeMessage;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.DPC.spring.entities.Calendrierexamen;
import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.PasswordResetToken;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.UtilisateurRepository;
import java.util.Arrays;
import java.util.Comparator;

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
	JavaMailSender javaMailSender;
	String emailClient="tataouine@gmail.com";
	public void sendMailWithAttachment(String toEmail,
			                           String body,
			                           String subject,
			                           String attchment) throws MessagingException {
		
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
		mimeMessageHelper.setFrom(emailClient);
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);
		
		FileSystemResource fileSystemResource= new FileSystemResource(new File(attchment));
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
		javaMailSender.send(mimeMessage);
		System.out.println("Mail with attachment sent successfully.");
		

	}
	
	
		
@Autowired
UtilisateurRepository userrepos ;
public Map<String, Boolean> RenisialiserMotdepasse(String emailDestinataire, List<Utilisateur> users) 
        throws NoSuchAlgorithmException, NoSuchPaddingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    Map<String, Boolean> success = new TreeMap<>();
    try {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // Construire le contenu HTML avec une table
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Bonjour, <br><br>")
                      .append("Voici la liste des utilisateurs :<br>")
                      .append("<table border='1' style='border-collapse: collapse; width: 100%;'>")
                      .append("<tr>")
                      .append("<th>Nom</th>")
                      .append("<th>Email</th>")
                      .append("</tr>");

        for (Utilisateur user : users) {
            contentBuilder.append("<tr>")
                          .append("<td>").append(user.getNom()).append("</td>")
                          .append("<td>").append(user.getEmail()).append("</td>")
                          .append("</tr>");
        }

        contentBuilder.append("</table>")
                      .append("<br>Cordialement,<br>Votre équipe.");

        // Ajouter le sujet, l'expéditeur, et le destinataire
        mimeMessageHelper.setSubject("Liste des utilisateurs");
        mimeMessageHelper.setFrom("votre.adresse@email.com"); // Remplacez par votre email
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
public Map<String, Boolean> envoyerCalendrier(String emailDestinataire, List<Calendrierexamen> calendrierList, Trimestre t, String typecalendrier) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    Map<String, Boolean> success = new TreeMap<>();

    try {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        // Grouper par `java.util.Date` et trier automatiquement
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
                .append("<br>Cordialement,<br>Scolarite.");

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


public Map<String, Boolean> emploie(String emailDestinataire, List<Emploidetemps> emploiList) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    Map<String, Boolean> success = new TreeMap<>();
    try {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        List<String> joursOrdre = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi");
        Utilisateur u = this.userrepos.findByEmail(emailDestinataire);
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Bonjour, <br><br> Cher Mr/Mme "+u.getNom()+" "+u.getPrenom() +" de la classe "+u.getClasse().getNomclasse()+" <br><br>")
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
                    contentBuilder.append("<td></td>");
                } else {
                    String contenuCellule = emplois.stream()
                            .map(e -> e.getMatiere().getNom() + " (" + e.getSalle().getNomdesalle() + ") <br>"+e.getUser().getNom()+" "+e.getUser().getPrenom() )
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




	public Map<String, Boolean> EnvoyerEmploi(String emailcrypter, Date date , String heure , String jour , String matiere , String salle , String prof )
	throws NoSuchAlgorithmException, NoSuchPaddingException {
	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	Map<String, Boolean> success = new TreeMap<String, Boolean>();
	List<PasswordResetToken> listpasswordResetToken = new ArrayList<>();
	try {
	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
	org.springframework.security.crypto.password.PasswordEncoder passwordEncorder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
	Utilisateur u = this.userrepos.findByEmail(emailcrypter);
	String email = encoder.encode(emailcrypter);
	String emails= email.replaceAll("/","-");
	PasswordResetToken tosave = new PasswordResetToken();
	success.put("response", true);

	mimeMessageHelper.setSubject("Emploi du temps");
	mimeMessageHelper.setFrom(email);
	mimeMessageHelper.setTo(emailcrypter);
	String content =" Bonjour Mr (Mme), "+u.getNom()+" "+u.getPrenom()+ " Voila la nouvelle emploie <br>"
			+ "Date : "+date+" ,<br><br>"
			+ "heure :"+heure+" ,<br><br>"
			+ "Jour:"+jour+",<br><br>"
			+ "Matiere :"+matiere+" ,<br><br>"
			+ "Salle:"+salle+",<br><br>"
			+"Prof:"+prof+",<br><br>"
			
					+ "Cordialement ,<br><br>" ;
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


	public Map<String, Boolean> calendrierExamen(String emailcrypter, Date date , String heure , String jour , String matiere , String salle , String prof,String type  )
			throws NoSuchAlgorithmException, NoSuchPaddingException {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			Map<String, Boolean> success = new TreeMap<String, Boolean>();
			List<PasswordResetToken> listpasswordResetToken = new ArrayList<>();
			try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			org.springframework.security.crypto.password.PasswordEncoder passwordEncorder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
			Utilisateur u = this.userrepos.findByEmail(emailcrypter);
			String email = encoder.encode(emailcrypter);
			String emails= email.replaceAll("/","-");
			PasswordResetToken tosave = new PasswordResetToken();
			success.put("response", true);

			mimeMessageHelper.setSubject("Calendrier Examen");
			mimeMessageHelper.setFrom(email);
			mimeMessageHelper.setTo(emailcrypter);
			String content =" Bonjour Mr (Mme), "+u.getNom()+" "+u.getPrenom()+ " Voila la nouvelle emploie <br>"
					+ "Date : "+date+" ,<br><br>"
					+ "heure :"+heure+" ,<br><br>"
					+ "Jour:"+jour+",<br><br>"
					+ "Matiere :"+matiere+" ,<br><br>"
					+ "Salle:"+salle+",<br><br>"
					+"Prof:"+prof+",<br><br>"
					+"Type Calendrier:"+type+",<br><br>"
					
							+ "Cordialement ,<br><br> Scolarite" ;
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


	
	
}
