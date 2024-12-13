package com.DPC.spring.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;  // Ensure this is Long and not String for JPA

    private String typeDisc;
    @ManyToOne
    Utilisateur user ;
    @ManyToOne
    Utilisateur eleve ; 
}
