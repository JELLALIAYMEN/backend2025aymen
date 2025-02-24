package com.DPC.spring.services;

import com.DPC.spring.entities.Departement;

import java.util.List;

public interface IDepartementService {
	 String Ajout(Departement d) ;
	 List<Departement> findAllDepartement() ;

}
