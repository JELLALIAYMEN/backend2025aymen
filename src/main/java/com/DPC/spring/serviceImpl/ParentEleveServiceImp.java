package com.DPC.spring.serviceImpl;

import com.DPC.spring.entities.Module;
import com.DPC.spring.entities.ParentEleve;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.Modulerep;
import com.DPC.spring.repositories.ParentElveRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.IParentEleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParentEleveServiceImp implements IParentEleveService {
    @Autowired
    UtilisateurRepository userrespo;
    @Autowired
    ParentElveRepository perepos;
@Autowired
Modulerep modulerep;
    @Override
    public List<String> getParentEmails(String eleveEmail) {
        Utilisateur eleve = this.userrespo.findByEmail(eleveEmail);

        List<ParentEleve> parentEleves = perepos.findByEleve(eleve);

        List<String> parentEmails = new ArrayList<>();

        if (parentEleves != null && !parentEleves.isEmpty()) {
            for (ParentEleve parentEleve : parentEleves) {
                String emailParent = parentEleve.getParent().getEmail();
                if (emailParent != null && !emailParent.isEmpty()) {
                    parentEmails.add(emailParent);
                }
            }
        }
        return parentEmails;
    }
    @Override
    public ResponseEntity<String> affecterTravail(String emailParent, String travailDescription) {
        // Trouver le parent par son email
        Utilisateur parent = perepos.findByEmail(emailParent);

        if (parent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent non trouvé");
        }

        // Création d'un nouvel objet Module (travail)
        Module module = new Module();
        module.setTravailafaire(travailDescription);
        module.setUt(parent); // Associer le travail à un parent (utilisateur)

        // Sauvegarder le module dans la base de données
        modulerep.save(module);

        return ResponseEntity.ok("Travail affecté avec succès au parent.");
    }
    @Override
    public List<Module> getTravauxParParent(String emailParent) {
        Utilisateur parent =perepos.findByEmail(emailParent);

        if (parent == null) {
            throw new RuntimeException("Parent non trouvé");
        }

        return modulerep.findByUt(parent);
    }
}

