package com.DPC.spring.entities;

import javax.persistence.Entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String nomjour ;
    private String heure ; 
    
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