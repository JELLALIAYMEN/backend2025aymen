package com.DPC.spring.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Utilisateur {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;


	private String nom;

 // ✅ Obligatoire
	private String prenom;

	 // ✅ Obligatoire
	private String password;

	 // ✅ Login unique
	private String login;

	 // ✅ Email unique
	private String email;

	 // ✅ Profil obligatoire
	private String profil;
	@Column(unique = true)
	@Size(min = 8, max = 8, message = "Le matricule doit contenir exactement 8 chiffres")
	private String matricule;

	private String libelle;

	private Boolean archiver;
     private Double MontantAnnuel;
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	private Date datecreation;

	@ManyToOne
	private Classe classe;



	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Payement> payements;

	@ManyToOne(fetch = FetchType.EAGER)
	// ✅ Évite les valeurs NULL
	private Autority authority;

	public Double getMontantAnnuel() {
		return MontantAnnuel;
	}

	public void setMontantAnnuel(Double montantAnnuel) {
		MontantAnnuel = montantAnnuel;
	}
}
