package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Reclamation;
import com.DPC.spring.entities.Utilisateur;

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {

	List<Reclamation> findByUserOrderByDateDesc(Utilisateur u);
	List<Reclamation> findByDestinataireOrderByDateDesc(Utilisateur u);


}
