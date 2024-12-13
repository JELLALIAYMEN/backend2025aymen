package com.DPC.spring.services;

import java.util.List;

import com.DPC.spring.entities.Salle;

public interface ISalleService {
String Ajout(Salle s, String nomdep);
List<Salle> afficher();
Salle afficherbyid(Long id);
String modif(Salle s,String nomdep);
}
