package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paiement {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private float prix ; 
	private boolean archiver ;
	@ManyToOne
	Credit credit ;
    @Enumerated(EnumType.STRING)
    ModalitePay modepay ;
    @Temporal(TemporalType.DATE)
	private Date date ;
}
