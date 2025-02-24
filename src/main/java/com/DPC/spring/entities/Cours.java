package com.DPC.spring.entities;

import javax.persistence.*;

import lombok.*;

@Entity
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nomcour;

	@Lob 
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
	@Column(name = "fichier", length = 1000)
    private String nom ; 
   @Column(name = "type")
	private String type;

   @ManyToOne
   Utilisateur user ; 
    
    @ManyToOne
    private Classe classe;

	@ManyToOne
	private Matiere matiere;

	public Cours(Long id, String nomcour, byte[] picByte, String nom, String type, Utilisateur user, Classe classe, Matiere matiere) {
		super();
		this.id = id;
		this.nomcour = nomcour;
		this.picByte = picByte;
		this.nom = nom;
		this.type = type;
		this.user = user;
		this.classe = classe;
		this.matiere= matiere;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomcour() {
		return nomcour;
	}

	public void setNomcour(String nomcour) {
		this.nomcour = nomcour;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Matiere getMatiere() {
		return matiere;
	}

	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}

	public Cours() {
		super();
	}


}