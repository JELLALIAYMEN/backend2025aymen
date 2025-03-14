package com.DPC.spring.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // For SQL database
    private Long id;

    private String nomclasse;



    @Override
    public String toString() {
        return "Classe{id=" + id + ", nomclasse='" + nomclasse + "'}";
    }
}