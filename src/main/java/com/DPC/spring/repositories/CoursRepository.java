package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Cours;
import com.DPC.spring.entities.Utilisateur;

public interface CoursRepository extends JpaRepository<Cours, Long> {

	List<Cours> findByClasse(Classe classe);

	List<Cours> findByUser(Utilisateur u);

}
