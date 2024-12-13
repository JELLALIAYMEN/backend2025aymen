package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Discipline;
import com.DPC.spring.entities.Utilisateur;

public interface DisciplineRepository extends JpaRepository<Discipline, Long>{

	List<Discipline> findByEleve(Utilisateur ue);

	List<Discipline> findByUser(Utilisateur up);

}
