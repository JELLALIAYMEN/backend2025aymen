package com.DPC.spring.controllers;

import com.DPC.spring.services.MailService;
import com.DPC.spring.services.Moduleservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Homework;
import com.DPC.spring.entities.Module; // ✅ Utilise ton entité Module
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.HomeworkRepository;
import com.DPC.spring.repositories.Modulerep;
import com.DPC.spring.repositories.UtilisateurRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("module")
@CrossOrigin("*")
public class Modulecontroller {
    private final Moduleservice Moduleserviceimpl;
@Autowired
Modulerep modulerepos ; 
@Autowired
UtilisateurRepository userrepos ; 
    public Modulecontroller(Moduleservice moduleserviceimpl) {
        Moduleserviceimpl = moduleserviceimpl;
    }
  
    @PostMapping("/save/{matricule}")
    public String saveModule(@RequestBody Module module, @PathVariable String matricule) {
        return   Moduleserviceimpl.save(module,matricule);
    }
    
    
    @GetMapping("utilisateur/matricule/modules")
    public List<Module> getModulesByMatricule( String matricule) {
    	Utilisateur u = this.userrepos.findByMatricule(matricule);
    	
    	return this.modulerepos.findByUt(u); 
    
    }
    @Autowired
    MailService mailservice ;
    @Autowired
    HomeworkRepository homeworkrepos ; 
    @PostMapping("envoyertravaille")
    public String envoyertravaille(String email, Long idmodule) {
    	Utilisateur u = this.userrepos.findByEmail(email);
    	Module m = this.modulerepos.findById(idmodule).get();
    	
    	Homework h = new Homework();
    	h.setModule(m);
    	h.setParent(u);
    	
    	 
    	
    	this.mailservice.envoyertravaillehomeword(email);
    	
    	return "true" ; 
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {
        try {
            Moduleserviceimpl .deleteById(id);
            return ResponseEntity.ok("Module deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable Long id, @RequestBody Module updatedModule) {
        Module updated = Moduleserviceimpl.updateModule(id, updatedModule);
        return ResponseEntity.ok(updated);
    }
    @GetMapping("all")
    public List<Module> findAll(){
        return  Moduleserviceimpl.findAll();
    }
}