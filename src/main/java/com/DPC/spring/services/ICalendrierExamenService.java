package com.DPC.spring.services;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import com.DPC.spring.entities.Calendrierexamen;
import com.DPC.spring.entities.Trimestre;

public interface ICalendrierExamenService {
	String Creercalendrier(Calendrierexamen calendrier , String email , String salles , String matiere , String classe ,String typecalendrier , Trimestre t ) throws NoSuchAlgorithmException, NoSuchPaddingException;

}
