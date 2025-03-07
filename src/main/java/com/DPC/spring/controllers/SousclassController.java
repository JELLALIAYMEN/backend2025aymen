package com.DPC.spring.controllers;

import com.DPC.spring.entities.Sousclass;
import com.DPC.spring.services.Sousclassservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("class")
@CrossOrigin("*") // Permettre les requêtes CORS
public class SousclassController {

    private final Sousclassservice sousclassservice;

    public SousclassController(Sousclassservice sousclassservice) {
        this.sousclassservice = sousclassservice;
    }

    // Endpoint pour ajouter une nouvelle sous-classe


    @PostMapping("/add")
    public ResponseEntity<Sousclass> addSousclass(@RequestBody Sousclass sousclass) {
        Sousclass newSousclass = sousclassservice.createSousclass(sousclass.getNomssousclasse());
        return ResponseEntity.ok(newSousclass);
    }
    // Endpoint pour récupérer toutes les sous-classes
    @GetMapping("/all")
    public ResponseEntity<List<Sousclass>> getAllSousclasses() {
        List<Sousclass> sousclasses = sousclassservice.sousclasses();
        return ResponseEntity.ok(sousclasses);
    }
}
