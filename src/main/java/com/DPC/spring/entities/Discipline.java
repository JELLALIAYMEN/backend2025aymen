package com.DPC.spring.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;  // Ensure this is Long and not String for JPA
    @Enumerated(EnumType.STRING)
    private StatusDisc statusDisc;

    private String cause;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String videoPath;
    @ManyToOne
    private Utilisateur eleve ;
    @ManyToOne
    private Utilisateur enseignant ;
    @ManyToOne
    private  Utilisateur user;
    private String adminComment;
}
