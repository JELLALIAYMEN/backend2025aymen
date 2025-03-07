package com.DPC.spring.serviceImpl;

import com.DPC.spring.entities.*;
import com.DPC.spring.repositories.Payrep;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.IPayementService;
import com.DPC.spring.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IPaymentServiceImp implements IPayementService {
    private final MailService emailService;
    private final UtilisateurRepository utilisateurRepository;
    private final Payrep payrep;
    private static final Logger logger = LoggerFactory.getLogger(IPaymentServiceImp.class);
    @Autowired
    private MailService mail;

    private String genererNotification(Utilisateur utilisateur, Modepay modepay, double creditRestant, Trimestre trimestre) {
        StringBuilder message = new StringBuilder();
        message.append("Cher ").append(utilisateur.getNom()).append(",\n\n");
        message.append("N'oubliez pas de régler votre facture scolaire du mois de janvier.");

        message.append(" d'un montant de ").append(131.7).append(" DT.\n");
        message.append("Le paiement doit être effectué avant le 15/01/2025.\n\n");
        message.append("Merci de régler votre situation financière.");
        return message.toString();
    }

    /**
     * Envoie une notification à l'adresse e-mail de l'utilisateur.
     */
    private void envoyerNotification(String email, String message) {
        try {
            emailService.sendSimpleMessage(email, "Mise à jour de votre paiement", message);
            logger.info("Notification envoyée avec succès à " + email);
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi de la notification : " + e.getMessage(), e);
        }
    }
    @Override
    public Double enregistrerPayement(String matricule, Double montantpay, String modepay, String modalitePay, String typepay, String statuspay, String date) {
        // Récupérer l'utilisateur par matricule
        Utilisateur U = utilisateurRepository.findByMatricule(matricule);

        if (U == null) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé pour le matricule : " + matricule);
        }

        // Création d'un nouvel objet Payement
        Payement payement = new Payement();
        payement.setMontantpay(montantpay);
        payement.setModepay(Modepay.valueOf(modepay));  // Vous pouvez ajouter un champ 'modepay' dans votre entité 'Payement'
        payement.setModalitePay(ModalitePay.valueOf(modalitePay)); // De même pour 'modalitePay'
        payement.setTypepay(Typepay.valueOf(typepay));  // Convertir le String 'typepay' en Enum 'Typepay'
        payement.setStatuspay(Statuspay.valueOf(statuspay));  // Associer le statuspay
         // Si 'trimestre' est présent, l'associer
        if (date != null) {
            payement.setDate(LocalDate.parse(date));  // Assurez-vous que le format de la date est correct
        } else {
            payement.setDate(LocalDate.now());  // Sinon, utilisez la date actuelle
        }
        payement.setUtilisateur(U);
       // payement.setUser(U);
       // payement.setUtilisateur(U);  // Associer le paiement à l'utilisateur

        // Ajouter le paiement à la liste de l'utilisateur
        if (U.getPayements() == null) {
            U.setPayements(new ArrayList<>());
        }
        U.getPayements().add(payement);

        // Enregistrer le paiement en base de données
        payrep.save(payement);

        // Mettre à jour l'utilisateur en base de données
        utilisateurRepository.save(U);

        // Retourner le montant payé
        return montantpay;
    }

    @Override
    public Map<Typepay, Double> calculerCreditsParTypepay(String matricule) {
        // Récupérer l'utilisateur par matricule
        Utilisateur U = utilisateurRepository.findByMatricule(matricule);

        if (U == null) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé pour le matricule : " + matricule);
        }

        // Définir le montant total à payer (montantAPayer)
        Double montantAPayer = U.getMontantAnnuel(); // Suppose que MontantAnnuel représente le total à payer

        if (montantAPayer == null) {
            throw new IllegalStateException("Le montant à payer n'est pas défini pour l'utilisateur.");
        }

        // Initialiser une map pour stocker les crédits par Typepay
        Map<Typepay, Double> creditsParTypepay = new HashMap<>();

        // Parcourir chaque type de paiement possible
        for (Typepay typepay : Typepay.values()) {
            // Récupérer tous les paiements de cet utilisateur pour le type donné
            List<Payement> paiements = U.getPayements().stream()
                    .filter(p -> p.getTypepay() == typepay)
                    .collect(Collectors.toList());

            // Calculer le montant total payé pour ce type de paiement
            Double montantTotalPayé = paiements.stream()
                    .mapToDouble(Payement::getMontantpay)
                    .sum();

            // Calculer le crédit pour ce type de paiement
            Double credit = montantAPayer - montantTotalPayé;

            // Stocker le crédit dans la map
            creditsParTypepay.put(typepay, credit);
        }

        // Retourner les crédits par Typepay
        return creditsParTypepay;
    }
    @Override
    public Double calculerCreditTotal(String matricule) {
        // Récupérer la map des crédits par Typepay
        Map<Typepay, Double> creditsParTypepay = calculerCreditsParTypepay(matricule);

        // Calculer la somme des crédits individuels
        Double creditTotal = creditsParTypepay.values().stream().mapToDouble(Double::doubleValue).sum();

        // Récupérer l'utilisateur par matricule
        Utilisateur U = utilisateurRepository.findByMatricule(matricule);

        if (U == null) {
            throw new IllegalArgumentException("Aucun utilisateur trouvé pour le matricule : " + matricule);
        }

        // Préparer le message de notification
        String nomComplet = U.getNom() + " " + U.getPrenom();
        String message = "Cher(e) " + nomComplet + ",\n\n" +
                "N'oubliez pas de régler votre crédit du mois, d'un montant de " + creditTotal + " FCFA. " +
                "Le paiement doit être effectué avant le 02/01/2025.";

        // Envoyer l'email avec les détails
        String sujet = "Notification de Crédit à Régler";
        String emailDestinataire = U.getEmail();

        mail.sendEmail(emailDestinataire, sujet, message);  // Passer correctement les paramètres

        // Retourner le crédit total
        return creditTotal;
    }



    @Override
    public Map<String, Double> calculerCreditsTotauxParClasse() {
        // Récupérer tous les utilisateurs
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();

        // Initialiser une map pour stocker les crédits totaux par classe
        Map<String, Double> creditsTotauxParClasse = new HashMap<>();

        // Parcourir chaque utilisateur pour calculer les crédits
        for (Utilisateur u : utilisateurs) {
            // Calculer les crédits totaux pour chaque élève
            Double creditTotal = calculerCreditTotal(u.getMatricule());

            // Récupérer le nom de la classe de l'utilisateur
            String nomClasse = u.getClasse().getNomclasse(); // Vous devez vous assurer que getIdClasse() retourne le nom de la classe

            // Additionner les crédits de cet élève au total de la classe
            creditsTotauxParClasse.put(nomClasse, creditsTotauxParClasse.getOrDefault(nomClasse, 0.0) + creditTotal);
        }

        // Retourner la map des crédits totaux par classe
        return creditsTotauxParClasse;
    }


}
