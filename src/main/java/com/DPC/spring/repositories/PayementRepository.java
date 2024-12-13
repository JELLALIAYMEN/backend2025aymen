package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Payement;
import com.DPC.spring.entities.Utilisateur;

public interface PayementRepository extends  JpaRepository<Payement,Long> {

	List<Payement> findByUser(Utilisateur u);

}
