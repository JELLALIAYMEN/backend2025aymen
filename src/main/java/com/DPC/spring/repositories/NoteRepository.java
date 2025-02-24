package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Note;
import com.DPC.spring.entities.Utilisateur;

public interface NoteRepository extends JpaRepository<Note, Long> {


}
