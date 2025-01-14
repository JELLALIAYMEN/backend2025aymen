package com.DPC.spring.serviceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Departement;
import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Nondisponible;
import com.DPC.spring.entities.Salle;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.repositories.EmploiRepository;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.repositories.NomdispoRepository;
import com.DPC.spring.repositories.SalleRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.IEmploiService;
import com.DPC.spring.services.MailService;

@Service
public class EmploiServiceImpl implements IEmploiService {
@Autowired
UtilisateurRepository userepos ;
@Autowired
EmploiRepository emploirepos ; 

@Autowired
DepartementRepository deparrepos ; 
@Autowired
Matiererep matrepos ; 
@Autowired
ClasseRepository classeRepos ; 
@Autowired
NomdispoRepository nondispo ; 
@Autowired
SalleRepository sallerepos ; 
@Autowired
MailService mailservice ; 
public String Creeremploi(Emploidetemps e , String email , String salles , String matiere , String classe ) throws NoSuchAlgorithmException, NoSuchPaddingException {
	Utilisateur p = this.userepos.findByEmail(email);
	Salle s = this.sallerepos.findByNomdesalle(salles);
	Matiere m = this.matrepos.findByNom(matiere);
	Classe c = this.classeRepos.findByNomclasse(classe);
	List<Utilisateur> list = this.userepos.findByClasse(c);
	
	Nondisponible existe = this.nondispo.findByNomjourAndUser(e.getNomjour(),p);
	if(existe!=null) {
		return "prof n'est pas disponible";
	}
	else
	{
		Emploidetemps emploiprofclasse = this.emploirepos.findByUserAndClasseAndNomjour(p,c,e.getNomjour());	
		if(emploiprofclasse!=null) {
			return "prof a ensiegne pour cette classe aujourd'hui";
		}
			
			else {
				Emploidetemps esalle = this.emploirepos.findBySalleAndNomjourAndHeure(s,e.getNomjour(),e.getHeure());
				if(esalle!=null) {
					return "salle n'est pas dispo";
				}
				else {
					e.setClasse(c);
					e.setMatiere(m);
					e.setSalle(s);
					e.setUser(p);
					this.emploirepos.save(e);
					for (int i = 0; i < list.size(); i++) {
						
						this.mailservice.EnvoyerEmploi(list.get(i).getEmail(), e.getDate(), e.getHeure(), e.getNomjour(), matiere, salles, p.getNom());
						
					}
					
					
					
					return  "true"; 
				}
			}
			
			
		

		
	
		
	}
	

	
	
		
	
	
}

	
}
