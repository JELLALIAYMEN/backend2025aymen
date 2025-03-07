package com.DPC.spring.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Payement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)

	private Modepay modepay; // Enumération des modes de paiement
private  double Montantpay;



	@DateTimeFormat(pattern = "dd-MM-yyyy")
	LocalDate date;



	@Enumerated(EnumType.STRING)

	private Typepay typepay; // Type de paiement (enum)

	private Double crédit; // Montant restant à payer

	@Enumerated(EnumType.STRING)

	private Statuspay statuspay;
	// Statut du paiement (enum)

	@Enumerated(EnumType.STRING)

	private ModalitePay modalitePay;
	@Override
	public String toString() {
		return "Payement{" +
				"id=" + id +
				", modepay=" + modepay +
				", date=" + date +
				", Montantpay=" + Montantpay +
				", typepay=" + typepay +
				", crédit=" + crédit +
				", statuspay=" + statuspay +
				", modalitePay=" + modalitePay +
				'}';
	}
	@ManyToOne
	@JoinColumn(name = "utilisateur_id")  // Assurez-vous que ce nom de colonne est correct
	private Utilisateur utilisateur;




	// Modalité de paiement (enum)
}