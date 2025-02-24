package com.DPC.spring.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class ReclamationDTO {
    private Long id;
    private String sujet;     // Sujet de la réclamation
    private String resultat;  // Détails de la réclamation
    private Date date;        // Date de la réclamation
    private String matricule;


}

