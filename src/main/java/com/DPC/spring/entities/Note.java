package com.DPC.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Clé primaire pour l'entité Note

    @Enumerated(EnumType.STRING)
    private TypeNote typeNote;
    @Column(nullable = false) // Enum pour définir le type de note (contrôle, examen, etc.)
   private Double Coff;
    private Double noteValue;  // Valeur de la note

    @Enumerated(EnumType.STRING)
    private Trimestre trimestre;  // Enum pour définir le trimestre

    // Relation ManyToOne avec Matiere
    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere mat;  // Matière associée à la note

    @ManyToOne
    Utilisateur user ;
}
