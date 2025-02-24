package com.DPC.spring.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Autority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true) // Évite les doublons de noms d'autorité
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "utilisateur_id") // Clé étrangère vers Utilisateur
	private Utilisateur utilisateur;



}
