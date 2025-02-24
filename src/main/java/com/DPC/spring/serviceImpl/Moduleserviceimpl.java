package com.DPC.spring.serviceImpl;

import com.DPC.spring.Mapper.Mapperdto;
import com.DPC.spring.entities.Module;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.MatiereRepository;
import com.DPC.spring.repositories.Modulerep;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.Moduleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Moduleserviceimpl implements Moduleservice {
    @Autowired
    Modulerep modulerep;
    @Autowired
    Mapperdto mapperdto;

    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;


    @Override
    public void deleteById(Long id) {
        modulerep.deleteById(id);

    }

    @Override
    public String save(Module module, String matricule) {
        Utilisateur utilisateur = utilisateurRepository.findByMatricule(matricule);
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur avec ce matricule non trouvé.");
        }

        // Création d'un nouveau module lié à l'utilisateur
        Module module1 = new Module();
        module1.setDate(module.getDate());
        module1.setContenue(module.getContenue());
        module1.setTravailafaire(module.getTravailafaire());
        module1.setNommodule(module.getNommodule());
        module1.setUt(utilisateur);

        this.modulerep.save(module1);
        return "true" ; 
    }

    @Override
    public List<Module> getModulesByMatricule(String matricule){
        Utilisateur utilisateur = utilisateurRepository.findByMatricule(matricule);
    	
      return  modulerep.findByUt(utilisateur);
    }

    @Override
    public Module updateModule(Long id, Module updatedModule) {
        // Find the existing module by ID
        Module existingModule =modulerep.findById(id).orElseThrow(() -> new RuntimeException("Module not found"));

        // Update fields of the existing module with the updated module's values
        existingModule.setNommodule(updatedModule.getNommodule());
        existingModule.setContenue(updatedModule.getContenue());
        existingModule.setTravailafaire(updatedModule.getTravailafaire());
        existingModule.setDate(updatedModule.getDate());


        // Update the module in the database
        return modulerep.save(existingModule);
    }

}
