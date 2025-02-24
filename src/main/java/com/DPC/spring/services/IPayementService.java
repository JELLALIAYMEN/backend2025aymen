package com.DPC.spring.services;

import com.DPC.spring.entities.*;

import java.time.LocalDate;
import java.util.Map;

public interface IPayementService {


   Double enregistrerPayement(String matricule, Double montantpay, String modepay, String modalitePay, String typepay, String statuspay, String date);

   Map<Typepay, Double> calculerCreditsParTypepay(String matricule);

   Double calculerCreditTotal(String matricule);

   Map<String, Double> calculerCreditsTotauxParClasse();

}
