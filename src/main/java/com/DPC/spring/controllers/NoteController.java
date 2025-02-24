package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.DTO.MatiereDTO;
import com.DPC.spring.DTO.NoteDTO;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.services.Inoteservice;

@CrossOrigin("*")
@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private final Inoteservice inoteserviceimple;
    @Autowired
    private Matiererep matiererep;

    public NoteController(Inoteservice inoteserviceimple) {
        this.inoteserviceimple = inoteserviceimple;
    }


    @PostMapping("/savenote")
    public NoteDTO save(@RequestBody NoteDTO noteDTO) {
        return inoteserviceimple.save(noteDTO);

    }


    @GetMapping("/{elId}/matiere/{idMatiere}/trimestre/{trimestre}")
    public List<NoteDTO> findByEl_IdAndTrimestreAndMat_Id(
            @PathVariable Long elId,
            @PathVariable Trimestre trimestre,
            @PathVariable Long idMatiere
    ) {
        // Appeler le service pour obtenir la liste des NoteDTO
        return inoteserviceimple.findByEl_IdAndTrimestreAndMat_Id(elId, trimestre, idMatiere);
    }
    @PostMapping("/save-matiere")
    public MatiereDTO saveMatiere(@RequestBody MatiereDTO matiereDTO) {


            // Convertir le DTO en entité Matiere
          // Sauvegarder la matière via le service

            return inoteserviceimple.saveMatiere(matiereDTO);}}