package com.DPC.spring.entities;

import javax.persistence.Entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emploidetemps {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;
    private String nomjour ;
    private String heure ; 
    private Boolean parqueinzeine ; 
    
    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Matiere matiere ; 
    @ManyToOne
    Utilisateur user ;
    



}