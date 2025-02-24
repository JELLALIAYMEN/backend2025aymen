package com.DPC.spring.repositories;

import com.DPC.spring.entities.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface Proffesseurrep extends JpaRepository<Professeur,Long> {
List<Professeur> findByDepartement(Long id);
}
