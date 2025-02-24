package com.DPC.spring.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DPC.spring.DTO.MatiereDTO;
import com.DPC.spring.DTO.NoteDTO;
import com.DPC.spring.Mapper.Mapperdto;
import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Note;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.TypeNote;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.repositories.Noterep;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.Inoteservice;

@Service
public class InoteServiceimpl implements  Inoteservice {
    @Autowired
    Noterep noterep;
    @Autowired
    UtilisateurRepository userrespo ; 
   
    @Autowired
    Matiererep matiererep;
    @Autowired
    Mapperdto mapperdto;
    @Override
    public MatiereDTO saveMatiere(MatiereDTO matiereDTO) {
        // Assurez-vous qu'un Eleve est fourni dans le DTO
        if (matiereDTO.getEleveId() == null) {
            throw new IllegalArgumentException("Un Eleve doit être fourni pour la Matiere.");
        }

        // Vérifiez que le champ niveau est valide




        // Recherchez l'Eleve dans la base de données
        Utilisateur eleve = userrespo.findById(matiereDTO.getEleveId()).get();
                
        // Créer et enregistrer la Matiere
        Matiere matiere = new Matiere();
        matiere.setNom(matiereDTO.getNom());
        matiere.setCoefficient(matiereDTO.getCoefficient());
         // Associez le niveau enum

        matiere = matiererep.save(matiere);

        return mapperdto.fromMatiere(matiere);
    }




    @Override
    public List<NoteDTO> findByEl_IdAndTrimestreAndMat_Id(Long elId, Trimestre trimestre, Long idMatiere) {
        // Récupérer les notes à partir du repository
    	Utilisateur u = this.userrespo.findById(elId).get();
        List<Note> notes = noterep.findByUserAndTrimestreAndMat_Id(u, trimestre, idMatiere);

        // Convertir la liste des notes en liste de NoteDTO
        return notes.stream()
                .map(note -> mapperdto.fromNote(note)) // Convertir chaque Note en NoteDTO
                .collect(Collectors.toList()); // Collecter les résultats dans une liste
    }

    @Override
    public NoteDTO save(NoteDTO noteDTO) {
        if (noteDTO == null) {
            throw new IllegalArgumentException("NoteDTO cannot be null");
        }

        // Convertir NoteDTO en entité Note
        Note note = mapperdto.fromNoteDTO(noteDTO);

        // Récupérer l'entité Matiere associée à partir de l'ID fourni dans le DTO
        Matiere matiere = matiererep.findById(noteDTO.getIdmatiere())
                .orElseThrow(() -> new IllegalArgumentException("Matiere with id " + noteDTO.getIdmatiere() + " not found"));
        note.setMat(matiere); // Associer Matiere à Note

        // Vérifiez et attribuez le coefficient en fonction du type de note
        if (noteDTO.getTypeNote() == TypeNote.NOTE_EXAMEN_SYNTHÈSE || noteDTO.getTypeNote() == TypeNote.NOTE_EXAMEN_SYNTHÈSE) {
            note.setCoff(2.0); // Coefficient 2 pour EXAMEN et SYNTHESE
        } else {
            note.setCoff(1.0); // Coefficient 1 pour les autres types de notes
        }

        // Récupérer l'entité Eleve associée à partir de l'ID fourni dans le DTO
        Utilisateur el = userrespo.findById(noteDTO.getIdel())
                .orElseThrow(() -> new IllegalArgumentException("Eleve with id " + noteDTO.getIdel() + " not found"));
        note.setUser(el); // Associer Eleve à Note

        System.out.println(note.getNoteValue()+"////");
        
        // Sauvegarder l'entité Note dans la base de données
        Note savedNote = noterep.save(note);

        // Retourner le NoteDTO correspondant à l'entité Note sauvegardée
        return mapperdto.fromNote(savedNote);
    }


}