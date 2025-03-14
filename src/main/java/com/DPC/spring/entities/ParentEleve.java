package com.DPC.spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ParentEleve {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id; 
	 	@ManyToOne
	 	Utilisateur eleve ;
	private String email ;
	 	@ManyToOne
	 	Utilisateur parent ; 

}
