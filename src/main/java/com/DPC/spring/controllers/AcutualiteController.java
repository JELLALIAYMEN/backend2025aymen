package com.DPC.spring.controllers;

import com.DPC.spring.entities.Actualite;
import com.DPC.spring.services.IActualiteService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("actualite")
@AllArgsConstructor
@RestController
public class AcutualiteController {
    private final IActualiteService iActualiteService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // Spécifie que la requête est multipart
    public ResponseEntity<Actualite> createActualite(
            @RequestPart("actualite") Actualite actualite,  // Utilise @RequestPart pour l'objet actualite
            @RequestPart(value = "video", required = false) MultipartFile video, // Fichier vidéo
            @RequestPart(value = "fichier", required = false) MultipartFile fichier) throws IOException { // Fichier supplémentaire
        Actualite createdActualite = iActualiteService.createActualite(actualite, video, fichier);
        return ResponseEntity.ok(createdActualite);
    }
    @GetMapping("/par-profil")
    public ResponseEntity<Page<Actualite>> getActualitesByCible(
            @RequestParam("profil") String profil,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        try {
            // Récupérer la page des actualités par profil
            Page<Actualite> actualitesPage = iActualiteService.getActualitesByCible(profil, page, size);
            return new ResponseEntity<>(actualitesPage, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // Retourner un bad request si profil est invalide
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Page<Actualite>> getAllActualites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
        try {
            Page<Actualite> actualitesPage = iActualiteService.getAllActualites(page, size);
            return new ResponseEntity<>(actualitesPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public Actualite getActualiteById(@PathVariable Long id) {
        return iActualiteService.getActualiteById(id);
    }
    @GetMapping("/{id}/video")
    public ResponseEntity<Resource> getActualiteVideo(@PathVariable Long id) throws IOException {
        Actualite actualite = getActualiteById(id);

        String videoPath = actualite.getVideoUrl();
        if (videoPath == null || videoPath.isEmpty()) {
            // Retourner une ressource par défaut ou un message de remplacement
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ByteArrayResource("Vidéo non disponible.".getBytes()));
        }

        File videoFile = new File(videoPath);
        if (!videoFile.exists()) {
            // Retourner une ressource par défaut ou un message de remplacement
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ByteArrayResource("Vidéo non trouvée.".getBytes()));
        }

        Resource resource = new UrlResource(videoFile.toURI());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getActualiteFile(@PathVariable Long id) throws IOException {
        Actualite actualite = getActualiteById(id);

        String filePath = actualite.getFichierUrl();
        if (filePath == null || filePath.isEmpty()) {
            // Retourner une ressource par défaut ou un message de remplacement
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ByteArrayResource("Fichier non disponible.".getBytes()));
        }

        File file = new File(filePath);
        if (!file.exists()) {
            // Retourner une ressource par défaut ou un message de remplacement
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ByteArrayResource("Fichier non trouvé.".getBytes()));
        }

        Resource resource = new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }



}
