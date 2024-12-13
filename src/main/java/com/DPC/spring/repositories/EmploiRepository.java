package com.DPC.spring.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.Salle;
import com.DPC.spring.entities.Utilisateur;

public interface EmploiRepository extends JpaRepository<Emploidetemps, Long> {

	Emploidetemps findByUserAndClasseAndDate(Utilisateur p, Classe c, Date date);

	Emploidetemps findByClasseAndDateAndHeure(Classe c, Date date, String heure);

	Emploidetemps findByUserAndClasseAndHeureAndNomjour(Utilisateur p, Classe c, String heure, String nomjour);

	Emploidetemps findByUserAndClasseAndNomjour(Utilisateur p, Classe c, String nomjour);

	Emploidetemps findByClasseAndNomjourAndHeure(Classe c, String nomjour, String heure);

	Emploidetemps findBySalleAndNomjourAndHeure(Salle s, String nomjour, String heure);

	List<Emploidetemps> findByUser(Utilisateur u);

	List<Emploidetemps> findByClasse(Classe c);

}
