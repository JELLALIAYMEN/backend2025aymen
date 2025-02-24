package com.DPC.spring.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Salle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nomdesalle;
	@Enumerated(EnumType.STRING)
	SalleType salleType;

	// Constructeur avec @JsonCreator pour la désérialisation
	@JsonCreator
	public Salle(@JsonProperty("id") Long id) {
		this.id = id;

	}
}