package com.DPC.spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credit {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private float prix ;
	private boolean archiver ;
	@ManyToOne
	private Utilisateur user ;
	@ManyToOne
	TypePaiement typep ;
	
}
