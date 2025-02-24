package com.DPC.spring.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Table(name = "menu")
public class Menu {
	  @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id; 
	  	@Enumerated(EnumType.STRING)
	    private TypeMenu Typemenu;
	  	private Date datedeb ;
	  	private Date datefin ;
	  	private String nomjour ;
		private  String  platentree;
		private String  platprincipale;
	private String dessert;
}
