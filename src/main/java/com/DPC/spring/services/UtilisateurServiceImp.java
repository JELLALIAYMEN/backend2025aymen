package com.DPC.spring.services;


import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Departement;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UtilisateurServiceImp implements  IUtilisateurService {

    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private DepartementRepository departementRepository;


    @Override
    public Utilisateur add(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Long idUtilisateur) {
        Utilisateur user1 = utilisateurRepository.findById(idUtilisateur).orElse(null);
        utilisateurRepository.delete(user1);
    }

    @Override
    public List<Utilisateur> GetUtilisateur() {
        List<Utilisateur> utilisateur = (List<Utilisateur>) utilisateurRepository.findByArchiverIsFalse();
        return utilisateur;

    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur user, Long id) {
        // Vérifier si l'utilisateur existe dans la base de données
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id).orElse(null);

        // Si l'utilisateur existe
        if (existingUtilisateur != null) {
            // Si les champs du "user" contiennent des valeurs différentes de l'existant, les mettre à jour
            if (user.getEmail() != null) {
                existingUtilisateur.setEmail(user.getEmail()); // Mise à jour de l'email si différent
            }

            if (user.getNom() != null) {
                existingUtilisateur.setNom(user.getNom()); // Mise à jour du nom si différent
            }

            if (user.getPrenom() != null) {
                existingUtilisateur.setPrenom(user.getPrenom()); // Mise à jour du prénom si différent
            }

            if (user.getClasse() != null) {
                existingUtilisateur.setClasse(user.getClasse()); // Mise à jour de la classe si différente
            }

            if (user.getAuthority() != null && !user.getAuthority().equals(existingUtilisateur.getAuthority())) {
                existingUtilisateur.setAuthority(user.getAuthority()); // Mise à jour des autorités
            }

            if (user.getArchiver() != null) {
                existingUtilisateur.setArchiver(user.getArchiver()); // Mise à jour de l'archivage si nécessaire
            }
            if (user.getLogin() != null) {
                existingUtilisateur.setLogin(user.getLogin()); // Mise à jour de l'archivage si nécessaire
            }
            if (user.getLibelle() != null) {
                existingUtilisateur.setLibelle(user.getLibelle()); // Mise à jour de l'archivage si nécessaire
            }


            // Sauvegarder l'utilisateur mis à jour dans la base de données
            return utilisateurRepository.save(existingUtilisateur);
        }

        // Si l'utilisateur n'existe pas, lancer une exception ou retourner un message spécifique
        throw new EntityNotFoundException("Utilisateur avec id " + id + " n'existe pas");
    }


    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Utilisateur> findAllByProfil(String profil) {
        return utilisateurRepository.findAllByProfil(profil);
    }




    @Override
    public Utilisateur affecterUtilisateurClasse(String email, Long id) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur!=null) {
            Classe classe = classeRepository.findById(id).orElse(null);
            if (classe != null) {
                utilisateur.setClasse(classe);
                return utilisateurRepository.save(utilisateur); // Save and return the actual object, not Optional
            } else {
                // Return null or throw exception if the classe is not found
                return null;
            }
        }
        // Return null if the utilisateur is not found
        return null;
    }


    @Override
    public Long countEleves() {
        return utilisateurRepository.countByProfilEleve();
    }

    @Override
    public Long countEnseignants() {
        return utilisateurRepository.countByProfilEnseignant();
    }

    @Override
    public List<Utilisateur> findByClasseNomclasse(String nomclasse) {
        return utilisateurRepository.findByClasseNomclasse(nomclasse);
    }


}