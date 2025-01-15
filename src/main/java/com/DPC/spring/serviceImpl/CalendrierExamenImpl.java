package com.DPC.spring.serviceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DPC.spring.entities.Calendrierexamen;
import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.Matiere;
import com.DPC.spring.entities.Nondisponible;
import com.DPC.spring.entities.Salle;
import com.DPC.spring.entities.Trimestre;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.CalendrierExamenReposository;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.DepartementRepository;
import com.DPC.spring.repositories.EmploiRepository;
import com.DPC.spring.repositories.Matiererep;
import com.DPC.spring.repositories.NomdispoRepository;
import com.DPC.spring.repositories.SalleRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.ICalendrierExamenService;
import com.DPC.spring.services.MailService;
@Service
public class CalendrierExamenImpl implements ICalendrierExamenService {
	@Autowired
	UtilisateurRepository userepos ;
	@Autowired
	CalendrierExamenReposository calendrierrepos ;  ; 

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
	
	
	public String Creercalendrier(Calendrierexamen calendrier , String email , String salles , String matiere , String classe ,String typecalendrier , Trimestre t ) throws NoSuchAlgorithmException, NoSuchPaddingException {
		Utilisateur p = this.userepos.findByEmail(email);
		if(p.getAuthorities().getName().equals("Teacher")) {
			
			
		Salle s = this.sallerepos.findByNomdesalle(salles);

		Matiere m = this.matrepos.findByNom(matiere);

		Classe c = this.classeRepos.findByNomclasse(classe);
		List<Utilisateur> list = this.userepos.findByClasse(c);
		
		
		if(typecalendrier.equals("synthese")) {
		
			Calendrierexamen calendrierexamen = this.calendrierrepos.findByClasseAndNomjourAndPeriode(c,calendrier.getNomjour(),calendrier.getPeriode());	
			if(calendrierexamen!=null) {
				return "cette classe a une devoir";
			}
			else {
				Calendrierexamen calendrierexamenuser = this.calendrierrepos.findByUserAndNomjourAndPeriode(p,calendrier.getNomjour(),calendrier.getPeriode());
				if(calendrierexamenuser!=null) {
					return "ce professeur a un devoir cette periode";
				}
				else {
					Calendrierexamen esalle = this.calendrierrepos.findBySalleAndNomjourAndPeriode(s,calendrier.getNomjour(),calendrier.getPeriode());
					if(esalle!=null) {
						return "salle n'est pas dispo";
					}
					else {
						calendrier.setClasse(c);
						calendrier.setMatiere(m);
						calendrier.setSalle(s);
						calendrier.setTrimestre(t);
						calendrier.setUser(p);
						calendrier.setTypecalendrier(typecalendrier);
						this.calendrierrepos.save(calendrier);
						
						for (int i = 0; i < list.size(); i++) {
							
							this.mailservice.calendrierExamen(list.get(i).getEmail(), calendrier.getDate(), calendrier.getPeriode(), calendrier.getNomjour(), matiere, salles, p.getNom(),typecalendrier);
							
						}
						
						
						
						
						return  "true"; 
					}
				}
				
				
			}

			
		
		}
		else {
			calendrier.setClasse(c);
			calendrier.setMatiere(m);
			calendrier.setSalle(s);
			calendrier.setUser(p);
			calendrier.setTrimestre(t);
			calendrier.setTypecalendrier(typecalendrier);
			this.calendrierrepos.save(calendrier);
			for (int i = 0; i < list.size(); i++) {
				
				this.mailservice.calendrierExamen(list.get(i).getEmail(), calendrier.getDate(), calendrier.getPeriode(), calendrier.getNomjour(), matiere, salles, p.getNom(),typecalendrier);
				
			}
			return  "true"; 	
		}
		}
		else {
			return "false";
		}

		
	}

	
		
			
		
		
	}


	
