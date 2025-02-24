package com.DPC.spring.services;

import java.util.Date;
import java.util.List;

import com.DPC.spring.DTO.ReclamationDTO;
import com.DPC.spring.entities.Reclamation;

public interface IReclamationService {
	ReclamationDTO reclamer(String email , String matricule , String sujet, Date date);
	List<Reclamation>all();
	Reclamation reclamatioyid(Long id);
	List<Reclamation> recbyemail(String email );
	List<Reclamation> findbydestinataire(String email );

	String reponse(Long id, String reponse) ;
	String supprimer(Long id);
}
