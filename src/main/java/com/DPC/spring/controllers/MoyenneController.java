package com.DPC.spring.controllers;

import javax.persistence.EntityNotFoundException;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.DTO.MoyenneDTO;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.services.Inoteservice;
import com.DPC.spring.services.Moyenneservice;

import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
@CrossOrigin("*")
@RestController
@RequestMapping("moyenne")
public class MoyenneController {
    @Autowired
    Moyenneservice Moyennesrviceimpl;

    @Autowired
    Inoteservice InoteServiceimpl;

    @PostMapping("/saveMoyenne")
    public MoyenneDTO saveMoyenne(@RequestBody MoyenneDTO moyenneDTO) {
        // Call the service method to save the Moyenne and return the saved DTO
        return Moyennesrviceimpl.saveMoyenne(moyenneDTO);
    }




    /*@GetMapping("/{elId}/matiere/{idMatiere}/trimestre/{trimestre}/moyenne")
    public Double calculerMoyenne(
            @PathVariable Long elId,
            @PathVariable Trimestre trimestre,
            @PathVariable Long idMatiere
    ) {
        return Moyennesrviceimpl.calculerMoyenneParMatiéreAndEleve(elId, trimestre, idMatiere);
    }*/


    @GetMapping("/{elId}/moyenne-annuelle")
    public Double getMoyenneAnnuelle(@PathVariable Long elId) {
        // Calculer la moyenne annuelle pour l'élève spécifié
        return Moyennesrviceimpl.calculerMoyenneAnnuelle(elId);
    }

    ///
    @GetMapping("/matiere")
    public ResponseEntity<Double> calculerMoyenneParMatiereEtEleve(
            @RequestParam Long eleveId,
            @RequestParam Trimestre trimestre,
            @RequestParam Long matiereId) {
        try {
            Double moyenne = Moyennesrviceimpl.calculerMoyenneParMatiéreAndEleve(eleveId, trimestre, matiereId);
            return ResponseEntity.ok(moyenne);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (EntityNotFoundException
        		e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/eleve/{elId}/trimestre/{trimestre}")
    public Double calculerMoyenneParEleveEtTrimestre(
            @PathVariable Long elId,
            @PathVariable Trimestre trimestre) {
        return    Moyennesrviceimpl.calculerEtEnregistrerMoyenneTrimestrielle(elId, trimestre);

    }
    @PutMapping("/moy/{idmoy}")
    public Double updateMoyenne(@PathVariable long idmoy,
                                                    @RequestParam Double moyennevalue) {

return  Moyennesrviceimpl.updateMoyenne(moyennevalue, idmoy);

}}
