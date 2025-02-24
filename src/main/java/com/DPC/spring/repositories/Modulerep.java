package com.DPC.spring.repositories;

import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Module; // ✅ Utilise ton entité Module

import java.util.List;


public interface Modulerep extends JpaRepository<Module,Long> {

    List<Module> findByUt(Utilisateur parent);
}
