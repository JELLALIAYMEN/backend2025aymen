package com.DPC.spring.DTO;

import java.io.IOException;
import java.util.Date;

import com.DPC.spring.entities.Actualite;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ActualiteDTO {
    private String actualite; // JSON en tant que chaîne
    private MultipartFile video; // Fichier vidéo
    private MultipartFile fichier; // Fichier supplémentaire

    // Getters et Setters

    public Actualite toEntity() {
        // Convertir JSON en entité Actualite
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(this.actualite, Actualite.class);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la conversion du JSON en Actualite", e);
        }
    }


}