package com.DPC.spring.repositories;

import com.DPC.spring.entities.Payement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Payrep extends JpaRepository<Payement, Long> {
    List<Payement> findByUtilisateur_Matricule(String  matricule);
}
