package com.DPC.spring.controllers;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.DTO.MoyenneDTO;
import com.DPC.spring.entities.Moyenne;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.MoyenneRep;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.Inoteservice;
import com.DPC.spring.services.Moyenneservice;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("moy")
@CrossOrigin("*")
public class MoyenneController {
    @Autowired
    Moyenneservice Moyennesrviceimpl;

    @Autowired
    Inoteservice InoteServiceimpl;
    @Autowired
    MoyenneRep moyrepos ;
    @PostMapping("/saveMoyenne")
    public MoyenneDTO saveMoyenne(@RequestBody MoyenneDTO moyenneDTO) {
        // Call the service method to save the Moyenne and return the saved DTO
        return Moyennesrviceimpl.saveMoyenne(moyenneDTO);
    }
    
    @GetMapping("afficher")
    public List<Moyenne> list(){
    	return this.moyrepos.findAll();
    }
@Autowired
UtilisateurRepository userrepos ;
    @GetMapping("afficherbyuser")
    public List<Moyenne> listbyuser(String email){
    	Utilisateur u = this.userrepos.findByEmail(email);
    	return this.moyrepos.findByUser(u);
    }




    @GetMapping("/{elId}/matiere/{idMatiere}/trimestre/{trimestre}/moyenne")
    public Double calculerMoyenne(
            @PathVariable Long elId,
            @PathVariable Trimestre trimestre,
            @PathVariable Long idMatiere
    ) {
        return Moyennesrviceimpl.calculerMoyenneParMatiéreAndEleve(elId, trimestre, idMatiere);
    }


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

}
    }
