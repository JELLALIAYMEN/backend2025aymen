package com.DPC.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nommodule;
    private String contenue;
    private String travailafaire;
    private Date date;
    @ManyToOne
    private Utilisateur ut ;
}


