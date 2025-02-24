package com.DPC.spring.entities;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assurez-vous que c'est bien une clé primaire
    @ManyToOne
    private Utilisateur parent ;
    @ManyToOne
    private Module module ; 
    
}
