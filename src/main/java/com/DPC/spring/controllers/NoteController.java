package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.DTO.MatiereDTO;
import com.DPC.spring.DTO.NoteDTO;
import com.DPC.spring.entities.Note;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.repositories.NoteRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.Inoteservice;


@RestController
@RequestMapping("note")
@CrossOrigin("*")
public class NoteController {
    @Autowired
    private final Inoteservice inoteserviceimple;
    @Autowired
    private Matiererep matiererep;

    public NoteController(Inoteservice inoteserviceimple) {
        this.inoteserviceimple = inoteserviceimple;
    }


    @PostMapping("/savenote")
    public NoteDTO save(@RequestBody NoteDTO noteDTO,String emailprof) {
        return inoteserviceimple.save(noteDTO,emailprof);

    }
    @Autowired
    NoteRepository noterepos ;
    @Autowired
    UtilisateurRepository userrepos ;
@GetMapping("allnote")
public List<Note>all(){
	return this.noterepos.findAll();
}
@GetMapping("notebyeleve")
public List<Note>notebyeleve(String emaileleve){
	Utilisateur u = this.userrepos.findByEmail(emaileleve);
	return this.noterepos.findByUser(u);
	
	
}

@GetMapping("notebyprof")
public List<Note>notebyprof(String emailprof){
	Utilisateur u = this.userrepos.findByEmail(emailprof);
	return this.noterepos.findByProf(u);
	
	
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