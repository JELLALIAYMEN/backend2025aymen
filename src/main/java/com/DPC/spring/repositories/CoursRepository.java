package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Cours;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    @Query("SELECT c FROM Cours c WHERE c.user.email = :email")
    List<Cours> findByUserEmail(@Param("email") String email);
    @Query("SELECT c FROM Cours c WHERE c.classe.id = :classeId")
    List<Cours> findCoursByClasse(@Param("classeId") Long classeId);

}
