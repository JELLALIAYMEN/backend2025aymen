package com.DPC.spring.services;

import java.util.List;

import com.DPC.spring.entities.Salle;
import com.DPC.spring.entities.SalleType;

public interface ISalleService {
String Ajout(Salle s, SalleType salleType);
List<Salle> afficher();
Salle afficherbyid(Long id);
String modif(Long id , String nom);
}
