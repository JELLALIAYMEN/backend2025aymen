package com.DPC.spring.repositories;

import com.DPC.spring.entities.Discipline;
import com.DPC.spring.entities.StatusDisc;
import com.DPC.spring.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    List<Discipline> findByEnseignant(Utilisateur enseignant);
    List<Discipline> findByEleveAndStatusDiscOrderByDateDesc(Utilisateur eleve, StatusDisc status);
    List<Discipline> findAllByOrderByDateDesc();

}
