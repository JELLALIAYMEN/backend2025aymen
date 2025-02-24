package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Classe;
import org.springframework.data.jpa.repository.Query;

public interface ClasseRepository extends JpaRepository<Classe,Long> {

	Classe findByNomclasse(String classe);
	@Query("SELECT COUNT (c) FROM Classe c")
	Long countAll();

}
