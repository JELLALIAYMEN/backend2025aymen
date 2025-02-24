package com.DPC.spring.services;

import com.DPC.spring.entities.Module;
import com.DPC.spring.entities.Utilisateur;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IUtilisateurService {

   // void  add(Utilisateur utilisateur);
    void  updateUtilisateur (Utilisateur utilisateur);
    void  deleteUtilisateur(Long idUtilisateur);
    List<Utilisateur> GetUtilisateur();
    Utilisateur updateUtilisateur(Utilisateur user, Long id);
    List<Utilisateur> findAllByProfil(String profil);
    List<Utilisateur> affecterUtilisateurDepartement(Long id, String email);
    Utilisateur affecterUtilisateurClasse(String email, Long id);
    Long countEleves();
    Long countEnseignants();
 //public List<Utilisateur> getElevesByClasse(String classe);
// public List<Utilisateur> getElevesByClasse(String classe) ;
 List<Utilisateur> findByClasseNomclasse(String nomclasse);

 }
