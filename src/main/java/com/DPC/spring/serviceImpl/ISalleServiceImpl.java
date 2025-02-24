package com.DPC.spring.serviceImpl;

import java.util.List;

import com.DPC.spring.entities.SalleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DPC.spring.entities.Departement;
import com.DPC.spring.entities.Salle;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.repositories.SalleRepository;
import com.DPC.spring.services.ISalleService;
@Service
public class ISalleServiceImpl implements ISalleService {
@Autowired
SalleRepository salerepos ; 
@Autowired
DepartementRepository deprepos ;
	@Override
	public String Ajout(Salle s, SalleType salleType) {
		if (s == null || salleType == null) {
			return "Erreur : La salle ou le type de salle est invalide.";
		}

		// Associer le type de salle à l'objet Salle
		s.setSalleType(salleType);

		// Sauvegarder la salle dans la base de données
		salerepos.save(s);

		return "Salle ajoutée avec succès sous le type : " + salleType;
	}




	public List<Salle> afficher(){
	return this.salerepos.findAll();
}
public Salle afficherbyid(Long id) {
	return this.salerepos.findById(id).get();
}
public String modif(Long id , String nom) {
	Salle s = this.salerepos.findById(id).get();
	s.setNomdesalle(nom);
	this.salerepos.saveAndFlush(s);
	return "true";
	
}
}
