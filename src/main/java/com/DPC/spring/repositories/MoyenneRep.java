package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Moyenne;

public interface MoyenneRep extends JpaRepository<Moyenne, Long> {

}
