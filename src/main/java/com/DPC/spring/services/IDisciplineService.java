package com.DPC.spring.services;

import com.DPC.spring.entities.Discipline;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IDisciplineService {
    Discipline ajouterDiscipline(MultipartFile file, LocalDate date, String cause, Long eleveId, Long enseignantId)throws IOException;
    Discipline getDisciplineById(Long id);
    List<Discipline> listDisciplinesByEnseignant(Long enseignantId);
    List<Discipline> getDisciplinesByStudentId(Long enseignantId);
    Discipline updateDiscipline(Long id, String cause, LocalDate date, MultipartFile file) throws IOException ;
    void deleteDiscipline(Long id);
    Discipline sendDisciplinetoAdmin(Long id);
    Discipline validerOuRefuser (Long id, String status, String adminComment);
    List<Discipline> listDisciplines();



}