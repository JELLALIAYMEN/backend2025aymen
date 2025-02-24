package com.DPC.spring.repositories;

import com.DPC.spring.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendrierExamenReposository extends JpaRepository<Calendrierexamen, Long> {

	Calendrierexamen findByClasseAndNomjourAndPeriode(Classe c, String nomjour, String periode);

	Calendrierexamen findByUserAndNomjourAndPeriode(Utilisateur p, String nomjour, String periode);

	Calendrierexamen findBySalleAndNomjourAndPeriode(Salle s, String nomjour, String periode);

	List<Calendrierexamen> findByClasse(Classe c);


	List<Calendrierexamen> findByUser(Utilisateur u);

	List<Calendrierexamen> findByClasseAndTypecalendrier(Classe c, Trimestre trimestre);

	List<Calendrierexamen> findByClasseAndTypecalendrierAndTypecalendrier(Classe c, Trimestre trimestre,
																		  String typecalendrier);

	List<Calendrierexamen> findByClasseAndTrimestreAndTypecalendrier(Classe c, Trimestre trimestre,
																	 String typecalendrier);

}
