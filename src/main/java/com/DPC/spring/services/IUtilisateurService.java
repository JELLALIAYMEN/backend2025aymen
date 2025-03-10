package com.DPC.spring.services;

import com.DPC.spring.entities.Utilisateur;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface IUtilisateurService {

   void  add(Utilisateur utilisateur);
    void  updateUtilisateur (Utilisateur utilisateur);

   // Utilisateur add(Utilisateur utilisateur);



    void  deleteUtilisateur(Long idUtilisateur);
    List<Utilisateur> GetUtilisateur();
    Utilisateur updateUtilisateur(Utilisateur user, Long id);
    List<Utilisateur> findAllByProfil(String profil);

    Utilisateur affecterUtilisateurClasse(String email, Long id);
    Long countEleves();
    Long countEnseignants();
 //public List<Utilisateur> getElevesByClasse(String classe);
// public List<Utilisateur> getElevesByClasse(String classe) ;
 List<Utilisateur> findByClasseNomclasse(String nomclasse);

 }
