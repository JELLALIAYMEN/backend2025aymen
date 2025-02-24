package com.DPC.spring.Mapper;


import com.DPC.spring.entities.Module; // ✅ Utilise ton entité Module

import com.DPC.spring.DTO.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.DPC.spring.entities.Discipline;
import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Moyenne;
import com.DPC.spring.entities.Note;
import com.DPC.spring.entities.Payement;
import com.DPC.spring.entities.Reclamation;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.services.Inoteservice;

import org.springframework.stereotype.Component;

@Component
public class Mapperdto {

    @Autowired
    Inoteservice InoteServiceimp;
    @Autowired
    Matiererep matiererep;

    public DisciplineDTO fromDiscipline(Discipline discipline) {
        if (discipline == null) {
            return null;
        }

        DisciplineDTO dto = new DisciplineDTO();
        BeanUtils.copyProperties(discipline, dto);
        return dto;
    }

    public Discipline fromDisciplineDTO(DisciplineDTO disciplineDTO) {
        if (disciplineDTO == null) {
            return null;
        }

        Discipline discipline = new Discipline();
        BeanUtils.copyProperties(disciplineDTO, discipline);
        return discipline;
    }

    public ReclamationDTO fromReclamation(Reclamation reclamation) {
        if (reclamation == null) {
            return null;
        }

        ReclamationDTO reclamationDTO = new ReclamationDTO();
        BeanUtils.copyProperties(reclamation, reclamationDTO);
        return reclamationDTO;
    }

    public Reclamation fromReclamationDTO(ReclamationDTO reclamationDTO) {
        if (reclamationDTO == null) {
            return null;
        }

        Reclamation reclamation = new Reclamation();
        BeanUtils.copyProperties(reclamationDTO, reclamation);
        return reclamation;
    }


    public Matiere fromMatiereDTO(MatiereDTO matiereDTO) {


        Matiere matiere = new Matiere();
        BeanUtils.copyProperties(matiereDTO, matiere);
        return matiere;
    }


    public Payement fromPayementDTO(PayementDTO payementDTO) {
        if (payementDTO == null) {
            return null;
        }

        Payement payement = new Payement();
        BeanUtils.copyProperties(payementDTO, payement);
        return payement;
    }

    public PayementDTO fromPayement(Payement payement) {
        if (payement == null) {
            return null;
        }

        PayementDTO payementDTO = new PayementDTO();
        BeanUtils.copyProperties(payement, payementDTO);
        return payementDTO;
    }

    public MoyenneDTO fromMoyenne(Moyenne moyenne) {
        if (moyenne == null) {
            return null;
        }
        MoyenneDTO moyenneDTO = new MoyenneDTO();
        BeanUtils.copyProperties(moyenne, moyenneDTO);
        return moyenneDTO;
    }


    public Moyenne fromMoyenDTO(MoyenneDTO moyenneDTO) {
        Moyenne moyenne = new Moyenne();
        BeanUtils.copyProperties(moyenneDTO, moyenne);
        return moyenne;
    }

    public Note fromNoteDTO(NoteDTO noteDTO) {
        Note note = new Note();
        note.setId(noteDTO.getId());
        note.setNoteValue(noteDTO.getNoteValue());
        note.setTypeNote(noteDTO.getTypeNote());
        note.setTrimestre(noteDTO.getTrimestre());

        // Assurez-vous que ces champs sont bien mappés
        note.setMat(matiererep.findById(noteDTO.getIdmatiere()).orElse(null));

        return note;
    }


    public NoteDTO fromNote(Note note) {
        NoteDTO noteDTO = new NoteDTO();

        // Copy basic properties
        BeanUtils.copyProperties(note, noteDTO);

        // Manually map idmatiere and idel from Matiere and Eleve
        if (note.getMat() != null) {
            noteDTO.setIdmatiere(note.getMat().getId()); // Assuming getId() returns Matiere ID
        }


        return noteDTO;
    }


    public MatiereDTO fromMatiere(Matiere matiere) {
        if (matiere == null) {
            return null;
        }

        MatiereDTO matiereDTO = new MatiereDTO();
        BeanUtils.copyProperties(matiere, matiereDTO);
        return matiereDTO;
    }


}