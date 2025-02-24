package com.DPC.spring.repositories;

import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Sousclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;


public interface Sousclassrep extends JpaRepository<Sousclass, Long> {
}
