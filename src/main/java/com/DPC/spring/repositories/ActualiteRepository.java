package com.DPC.spring.repositories;

import com.DPC.spring.entities.Actualite;
import com.DPC.spring.entities.Cible;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActualiteRepository extends JpaRepository<Actualite, Long> {
    List<Actualite> findByUtilisateursCiblesId(Long utilisateurId);
    Page<Actualite> findByCible(Cible cible, Pageable pageable);
}
