package com.DPC.spring.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

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
	private String nom ;
	private String prenom ;
	private String password ;
	private String login ;
	private String email ;
	private String profil ;
	private String matricule ;
	private String libelle ;

	private Boolean archiver ;
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	private Date datecreation ;

	@ManyToOne
	Classe classe ;
	@ManyToOne
	private  Departement departement;
	private Double MontantAnnuel;

	@OneToMany(mappedBy="utilisateur")
	List<Payement> payements;
 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authority_id", nullable = false) // Évite les valeurs NULL
	private Autority authority;
}
