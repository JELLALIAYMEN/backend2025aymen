package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Calendrierexamen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)

    private Date date;
    private String nomjour ;
    private String periode ;
    private String typecalendrier ;
    @Enumerated(EnumType.STRING)
    private Trimestre trimestre ;
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
