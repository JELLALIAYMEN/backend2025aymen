package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Calendrierexamen;
import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Salle;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.Utilisateur;

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
