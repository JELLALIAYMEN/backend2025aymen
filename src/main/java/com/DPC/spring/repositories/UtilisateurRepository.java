package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DPC.spring.entities.Utilisateur;
import org.springframework.stereotype.Repository;

@Repository

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
	Utilisateur findByEmail(String email);
    Utilisateur findById (long idUtilisateur) ;
	//Utilisateur findByEmail(String email);
	Utilisateur findByLibelle(String libelle);
	List<Utilisateur> findByArchiverIsFalse();
	@Query(nativeQuery=true,value="select COUNT(*) FROM utilisateur where archiver=0")
	Long countuser ();
	@Query(nativeQuery=true,value="select COUNT(*) FROM menmbre")
	Long countmembre ();
	@Query(nativeQuery=true,value="select COUNT(*) FROM joueur")
	Long countjoueur ();
	@Query(nativeQuery=true,value="select COUNT(*) FROM document")
	Long countdocument ();

	List<Utilisateur> findAllByProfil(String profil);
	Utilisateur findByProfil(String profil);
	Utilisateur findByMatricule(String matricule);

	@Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.profil = 'eleve'")
	Long countByProfilEleve();

	@Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.profil = 'enseignant'")
	Long countByProfilEnseignant();
	List<Utilisateur> findByClasseNomclasse(String nomclasse);
	//List<Utilisateur> findByClasse(String c);
	//List<Utilisateur> findByClasse_Nomclasse(String nomclasse);

}
