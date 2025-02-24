package com.DPC.spring.serviceImpl;

import com.DPC.spring.entities.Actualite;
import com.DPC.spring.entities.Cible;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ActualiteRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.IActualiteService;
import com.DPC.spring.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ActualiteServiceImp implements IActualiteService {
    private final ActualiteRepository actualiteRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FileStorageService fileStorageService;

    private final MailService mailService;
    @Override
    public Actualite createActualite(Actualite actualite, MultipartFile video, MultipartFile fichier) throws IOException {
        actualite.setDatePublication(new Date());

        // Gérer les fichiers si présents
        if (video != null && !video.isEmpty()) {
            String videoPath = fileStorageService.saveFile(video);
            actualite.setVideoUrl(videoPath);
        }

        if (fichier != null && !fichier.isEmpty()) {
            String fichierPath = fileStorageService.saveFile(fichier);
            actualite.setFichierUrl(fichierPath);
        }
        if (actualite.getCible().equals(Cible.ELEVE)) {
            List<Utilisateur> utilisateursCibles = notifyUsers("eleve", "Nouvelle Actualité pour les Élèves", actualite.getTitre());
            actualite.setUtilisateursCibles(utilisateursCibles);
        } else if (actualite.getCible().equals(Cible.ENSEIGNANT)) {
            List<Utilisateur> utilisateursCibles = notifyUsers("enseignant", "Nouvelle Actualité pour les Enseignants", actualite.getTitre());
            actualite.setUtilisateursCibles(utilisateursCibles);
        }
        Actualite savedActualite = actualiteRepository.save(actualite);

        return savedActualite;
    }

    private List<Utilisateur> notifyUsers(String profil, String subject, String actualiteTitre) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAllByProfil(profil);
        List<Utilisateur> utilisateursNotifies = new ArrayList<>();  // Liste des utilisateurs à qui le mail a été envoyé

        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé pour le profil : " + profil);
            return utilisateursNotifies;
        }

        for (Utilisateur utilisateur : utilisateurs) {
            try {
                String content = "Bonjour " + utilisateur.getNom() + ",\n\n" +
                        "Une nouvelle actualité a été publiée : " + actualiteTitre + ".\n\n" +
                        "Veuillez consulter votre espace pour voir la publication en détail.\n\n" +
                        "Cordialement.";
                mailService.sendMail(utilisateur.getEmail(), subject, content);
                utilisateursNotifies.add(utilisateur);  // Ajouter l'utilisateur à la liste des notifiés
            } catch (Exception e) {
                System.err.println("Erreur lors de l'envoi d'email à " + utilisateur.getEmail());
                e.printStackTrace();
            }
        }

        return utilisateursNotifies;
    }

    public Page<Actualite> getAllActualites(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("datePublication")));
        return actualiteRepository.findAll(pageable);  // Renvoie un objet Page contenant les actualités
    }

    @Override
    public Page<Actualite> getActualitesByCible(String profil, int page, int size) {
        // Vérification de null ou chaîne vide
        if (profil == null || profil.trim().isEmpty()) {
            throw new IllegalArgumentException("Le profil ne peut pas être null ou vide.");
        }

        // Mapping du profil à la cible
        Cible cible;
        switch (profil.toLowerCase()) { // Ignorer la casse pour la comparaison
            case "eleve":
                cible = Cible.ELEVE;
                break;
            case "enseignant":
                cible = Cible.ENSEIGNANT;
                break;
            default:
                throw new IllegalArgumentException("Profil non reconnu : " + profil);
        }

        // Création de l'objet Pageable pour la pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("datePublication")));  // Trier par date de publication si nécessaire

        // Retourner la page des actualités correspondant à la cible
        return actualiteRepository.findByCible(cible, pageable);
    }



    @Override
    public Actualite getActualiteById(Long id) {
        Actualite actualite = actualiteRepository.findById(id).orElse(null);
        return actualite;
    }


}
