package com.DPC.spring.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DPC.spring.DTO.MoyenneDTO;
import com.DPC.spring.DTO.NoteDTO;
import com.DPC.spring.Mapper.Mapperdto;
import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Moyenne;
import com.DPC.spring.entities.MoyenneType;
import com.DPC.spring.entities.Note;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.TypeNote;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.repositories.MoyenneRep;
import com.DPC.spring.repositories.Noterep;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.Inoteservice;
import com.DPC.spring.services.Moyenneservice;

@Service
public class MoyenneServiceimpl  implements Moyenneservice {


    @Autowired
    private MoyenneRep moyenneRep;

    @Autowired
    UtilisateurRepository userrespo ; 
    @Autowired
    private Mapperdto dto;
    @Autowired
    Noterep noterep;

    @Autowired
    private UtilisateurRepository userrep;
    @Autowired
    private Matiererep matiererep;

    @Autowired
    private Inoteservice inoteServiceimpl;
    @Autowired
    Mapperdto mapperdto;



    @Override
    public MoyenneDTO saveMoyenne(MoyenneDTO moyenneDTO) {
        if (moyenneDTO == null) {
            throw new IllegalArgumentException("moyenneDTO cannot be null");
        }

        Utilisateur el = userrep.findById(moyenneDTO.getIdel())
                .orElseThrow(() -> new IllegalArgumentException("Eleve with id " + moyenneDTO.getIdel() + " not found"));

        // Convertir MoyenneDTO en entité Moyenne
        Moyenne moyenne = mapperdto.fromMoyenDTO(moyenneDTO);
        moyenne.setUser(el); // Associe l'élève récupéré à l'entité Moyenne

        // Sauvegarde de la moyenne dans la base de données
        Moyenne savedMoyenne = moyenneRep.save(moyenne);

        // Retourner l'objet MoyenneDTO correspondant
        return mapperdto.fromMoyenne(savedMoyenne);
    }

    @Override
    public List<NoteDTO> findByidmatiereAndidel(Long idmatiere) {
        Matiere matiere = matiererep.findById(idmatiere).orElse(null);

        List<Note> notes = noterep.findAll();
        return notes.stream().map(note -> dto.fromNote(note)).collect(Collectors.toList());
    }

    @Override
    public Double calculerMoyenneAnnuelle(Long elId) {

        double sommeDesMoyennesPonderees = 0.0;
        double sommeDesCoefficients = 0.0;

        // Coefficients des trimestres
        double coefficientTrimestre1 = 1.0;
        double coefficientTrimestre2 = 2.0;
        double coefficientTrimestre3 = 2.0;

        // Calcul des moyennes trimestrielles
        Double moyenneTrimestre1 = calculerEtEnregistrerMoyenneTrimestrielle(elId, Trimestre.Trimestre1);
        Double moyenneTrimestre2 = calculerEtEnregistrerMoyenneTrimestrielle(elId, Trimestre.Trimestre2);
        Double moyenneTrimestre3 = calculerEtEnregistrerMoyenneTrimestrielle(elId, Trimestre.Trimestre3);

        // Ajout des moyennes pondérées
        sommeDesMoyennesPonderees += moyenneTrimestre1 * coefficientTrimestre1;
        sommeDesMoyennesPonderees += moyenneTrimestre2 * coefficientTrimestre2;
        sommeDesMoyennesPonderees += moyenneTrimestre3 * coefficientTrimestre3;

        // Somme des coefficients
        sommeDesCoefficients += coefficientTrimestre1 + coefficientTrimestre2 + coefficientTrimestre3;

        // Calcul de la moyenne annuelle
        Double moyenneAnnuelle = sommeDesCoefficients > 0 ? sommeDesMoyennesPonderees / sommeDesCoefficients : 0.0;

        // Enregistrer la moyenne annuelle dans la base de données
        Moyenne moyenne = new Moyenne();
        moyenne.setUser(userrep.findById(elId).orElseThrow(() -> new IllegalArgumentException("Élève introuvable.")));
        moyenne.setMoyenneType(MoyenneType.moyenneAnnuelle);
        moyenne.setMoyennevalue(moyenneAnnuelle);


        moyenneRep.save(moyenne);

        return moyenneAnnuelle;}

    @Override
    public Double calculerEtEnregistrerMoyenneTrimestrielle(Long elId, Trimestre trimestre) {
        // Récupérer toutes les matières pour l'élève
        List<Matiere> matieres = matiererep.findAll();

        if (matieres.isEmpty()) {
            throw new IllegalArgumentException("Aucune matière trouvée pour cet élève.");
        }

        double sommeDesMoyennes = 0.0;
        double nombreDeMatieres = 0.0;

        for (Matiere matiere : matieres) {
            try {
                Double moyenneMatiere = calculerMoyenneParMatiéreAndEleve(elId, trimestre, matiere.getId());
                
                
                sommeDesMoyennes += moyenneMatiere;
                nombreDeMatieres++;
            } catch (IllegalArgumentException e) {
                // Ignorer les matières sans notes
                System.out.println("Pas de notes pour la matière : " + matiere.getNom());
            }
        }

        Double moyenneTrimestrielle = nombreDeMatieres > 0 ? sommeDesMoyennes / nombreDeMatieres : 0.0;

        // Enregistrer la moyenne dans la base de données
        Moyenne moyenne = new Moyenne();
        moyenne.setUser(userrep.findById(elId).orElseThrow(() -> new IllegalArgumentException("Élève introuvable.")));
        moyenne.setTrimestre(trimestre);
        moyenne.setMoyenneType(MoyenneType.valueOf("moyenneTrimestrielle" + (trimestre.ordinal() + 1)));



        moyenne.setMoyennevalue(moyenneTrimestrielle);

        moyenneRep.save(moyenne);

        return moyenneTrimestrielle;
    }



    @Override
    public Double updateMoyenne(Double moyennevalue, Long idmoy) {
        // 1. Récupérer la moyenne existante depuis la base de données par idmoy
        Optional<Moyenne> optionalMoyenne =moyenneRep.findById(idmoy);

        // Vérifier si la moyenne existe
        if (!optionalMoyenne.isPresent()) {
            throw new IllegalArgumentException("La moyenne avec l'id " + idmoy + " n'existe pas.");
        }

        // 2. Mettre à jour la valeur de la moyenne
        Moyenne moyenne = optionalMoyenne.get();
        moyenne.setMoyennevalue(moyennevalue);

        // 3. Sauvegarder la moyenne mise à jour dans la base de données
        Moyenne updatedMoyenne = moyenneRep.save(moyenne);

        // 4. Mapper l'objet Moyenne mis à jour en MoyenneDTO
        MoyenneDTO moyenneDTO = new MoyenneDTO();
        moyenneDTO.setIdmoy(updatedMoyenne.getId().toString()); // Utiliser l'ID sous forme de chaîne de caractères
        moyenneDTO.setMoyennevalue(updatedMoyenne.getMoyennevalue());
        moyenneDTO.setIdel(updatedMoyenne.getUser().getId());
        moyenneDTO.setTrimestre(updatedMoyenne.getTrimestre());
        moyenneDTO.setMoyenneType(updatedMoyenne.getMoyenneType());

        return moyenneDTO.getMoyennevalue();
    }




    @Override
    public Double calculerMoyenneParMatiéreAndEleve(Long elId, Trimestre trimestre, Long idMatiere) {
        // Récupérer les notes de l'élève pour la matière donnée et le trimestre
    	Utilisateur u = this.userrespo.findById(elId).get();
        List<Note> notes = noterep.findByUserAndTrimestreAndMat_Id(u, trimestre, idMatiere);

        // Vérifier si des notes existent pour éviter une division par zéro
        if (notes.isEmpty()) {
            throw new IllegalArgumentException("Aucune note trouvée pour cet élève, matière et trimestre.");
        }

        // Variables pour le calcul de la moyenne
        double sommeDesNotes = 0.0;
        double sommeDesCoefficients = 0.0;

        // Boucle pour calculer la somme des notes pondérées et des coefficients
        for (Note note : notes) {
            double coefficient = 1.0; // Coefficient par défaut

            // Déterminer le coefficient en fonction du type de note
            if (note.getTypeNote() == TypeNote.NOTE_EXAMEN_SYNTHÈSE) {
                coefficient = 2.0; // Coefficient pour les examens de synthèse
            }

            // Ajouter la note pondérée à la somme des notes
            sommeDesNotes += note.getNoteValue() * coefficient;
            // Ajouter le coefficient à la somme des coefficients
            sommeDesCoefficients += coefficient;
        }

        // Calculer la moyenne en vérifiant que la somme des coefficients n'est pas nulle
        return sommeDesCoefficients > 0 ? sommeDesNotes / sommeDesCoefficients : 0.0;
    }





}