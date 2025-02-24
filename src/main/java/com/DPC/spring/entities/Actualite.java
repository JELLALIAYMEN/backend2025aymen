package com.DPC.spring.entities;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Actualite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String texte;
    private String videoUrl;
    private String fichierUrl;
    private Date datePublication;
    private Cible cible;
    @ManyToMany
    @JoinTable(
            name = "actualite_utilisateur",
            joinColumns = @JoinColumn(name = "actualite_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id"))
    private List<Utilisateur> utilisateursCibles;
}
