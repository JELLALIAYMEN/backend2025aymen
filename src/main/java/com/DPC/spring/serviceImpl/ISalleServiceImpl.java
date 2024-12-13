package com.DPC.spring.serviceImpl;

import java.util.List;

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

public String Ajout(Salle s, String nomdep) {
	Departement d = this.deprepos.findByNom(nomdep);
	Salle sexiste = this.salerepos.findByNomdesalle(s.getNomdesalle());
	if(sexiste==null) {
		s.setDepartement(d);
		this.salerepos.save(s);
		return "true";
	}
	else {
		return "false";
	}
	
}
public List<Salle> afficher(){
	return this.salerepos.findAll();
}
public Salle afficherbyid(Long id) {
	return this.salerepos.findById(id).get();
}
public String modif(Salle s,String nomdep) {
	Salle sa = this.salerepos.findById(s.getId()).get();
	Departement d = this.deprepos.findByNom(nomdep);

	s.setDepartement(d);
	sa =this.salerepos.saveAndFlush(s);
	return "true";
	
}
}
