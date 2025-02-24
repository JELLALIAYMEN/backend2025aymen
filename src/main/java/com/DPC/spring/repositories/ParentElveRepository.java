package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.ParentEleve;
import com.DPC.spring.entities.Utilisateur;

public interface ParentElveRepository extends JpaRepository<ParentEleve,Long> {

	List<ParentEleve> findByParent(Utilisateur up);
	List<ParentEleve> findByEleve(Utilisateur eleve);
	List<ParentEleve> findByParent_Email(String email);

	// Recherche par l'email de l'élève
	List<ParentEleve> findByEleve_Email(String email);

	Utilisateur findByEmail(String emailParent);
}
