package com.DPC.spring.controllers;

import com.DPC.spring.entities.Discipline;
import com.DPC.spring.serviceImpl.DisciplineServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;


@CrossOrigin("*")
@RestController
@RequestMapping("discipline")
public class DsciplinecController {
    @Autowired
    private DisciplineServiceImp disciplineService;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<Discipline> ajouterDiscipline(
            @RequestParam("file") MultipartFile file,
            @RequestParam("date") String date,
            @RequestParam("cause") String cause,
            @RequestParam("eleveId") Long eleveId,
            @RequestParam("enseignantId") Long enseignantId
    ) throws IOException {
        // Convertir la date de String à LocalDate
        LocalDate localDate = LocalDate.parse(date);

        // Appeler le service pour traiter et sauvegarder la discipline
        Discipline discipline = disciplineService.ajouterDiscipline(file, localDate, cause, eleveId, enseignantId);

        return ResponseEntity.ok(discipline);
    }
    @GetMapping("/{id}")
    public Discipline getDisciplineById(@PathVariable Long id) {
        return disciplineService.getDisciplineById(id);
    }
    // Lister toutes les disciplines pour un enseignant
    @GetMapping("/byEnseignant")
    public ResponseEntity<List<Discipline>> listDisciplinesByEnseignant(@RequestParam Long enseignantId) {
        List<Discipline> disciplines = disciplineService.listDisciplinesByEnseignant(enseignantId);
        return ResponseEntity.ok(disciplines);
    }
    @GetMapping("/byeleve")
    public ResponseEntity<List<Discipline>> listDisciplinesByEleve(@RequestParam Long eleveId) {
        List<Discipline> disciplines = disciplineService.getDisciplinesByStudentId(eleveId);
        return ResponseEntity.ok(disciplines);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Discipline>> listDisciplines() {
        List<Discipline> disciplines = disciplineService.listDisciplines();
        return ResponseEntity.ok(disciplines);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Discipline> updateDiscipline(
            @PathVariable Long id,
            @RequestParam(required = false) String cause,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) MultipartFile file) {

        try {
            LocalDate localDate = LocalDate.parse(date);
            Discipline updatedDiscipline = disciplineService.updateDiscipline(id, cause, localDate, file);
            return ResponseEntity.ok(updatedDiscipline);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/validerOuRefuser/{id}")
    public ResponseEntity<Discipline> validateAndSend(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestParam("adminComment") String adminComment ) {

        try {
            Discipline updatedDiscipline = disciplineService.validerOuRefuser(id,status, adminComment);
            return ResponseEntity.ok(updatedDiscipline);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // Supprimer une discipline
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    // Changer le statut d'une discipline
    @PatchMapping("/{id}/sendToAdmin")
    public ResponseEntity<Discipline> changeStatus(@PathVariable Long id) {
        Discipline updatedDiscipline = disciplineService.sendDisciplinetoAdmin(id);
        return ResponseEntity.ok(updatedDiscipline);
    }

    @GetMapping("/{id}/video")
    public ResponseEntity<Resource> getDisciplineVideo(@PathVariable Long id) throws IOException {
        Discipline discipline = getDisciplineById(id);

        String videoPath = discipline.getVideoPath();
        if (videoPath == null || videoPath.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        File videoFile = new File(videoPath);
        if (!videoFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(videoFile.toURI());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
