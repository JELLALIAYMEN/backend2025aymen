package com.DPC.spring.services;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import com.DPC.spring.entities.Calendrierexamen;
import com.DPC.spring.entities.Trimestre;

public interface ICalendrierExamenService {
	public String Creercalendrier(Calendrierexamen calendrier , String libelle , String salles , String matiere , String classe, String typecalendrier,Trimestre t )throws NoSuchAlgorithmException, NoSuchPaddingException;

}
