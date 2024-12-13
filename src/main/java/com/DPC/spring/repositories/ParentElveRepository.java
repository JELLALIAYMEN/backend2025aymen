package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.ParentEleve;
import com.DPC.spring.entities.Utilisateur;

public interface ParentElveRepository extends JpaRepository<ParentEleve,Long> {

	List<ParentEleve> findByParent(Utilisateur up);

	ParentEleve findByParentAndEleve(Utilisateur ue, Utilisateur up);

}
