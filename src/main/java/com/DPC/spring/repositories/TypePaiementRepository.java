package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.TypePaiement;

public interface TypePaiementRepository extends JpaRepository<TypePaiement,Long> {

	TypePaiement findByType(String type);

}
