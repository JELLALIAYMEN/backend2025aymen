package com.DPC.spring.controllers;

import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.Payement;
import com.DPC.spring.entities.Typepay;
import com.DPC.spring.services.IPayementService;
import com.DPC.spring.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("paiement")
@AllArgsConstructor
public class Paycontroller {

    private final IPayementService payementService;
    @Autowired
    MailService mail ;
    @PostMapping("/enregistrer")
    public Double enregistrerPayement(
            @RequestParam String matricule,
            @RequestParam Double montantpay,
            @RequestParam String modepay,
            @RequestParam String modalitePay,
            @RequestParam String typepay,
            @RequestParam String statuspay,

            @RequestParam(required = false) String date) {

        // Call service to process the payment
        return payementService.enregistrerPayement(matricule, montantpay, modepay, modalitePay, typepay, statuspay,  date);
    }

    @PostMapping("envoiemail")
    public String envoi(@RequestParam String emailDestinataire, @RequestParam String matricule,
                        @RequestParam Double montantpay, @RequestParam Typepay typepay, @RequestParam Date date)
            throws NoSuchAlgorithmException, NoSuchPaddingException {

        // Appeler la méthode EnvoyerPayement avec les paramètres nécessaires
        Map<String, Object> response = this.mail.EnvoyerPayement(emailDestinataire, matricule, montantpay, typepay, date);

        // Vérifier si l'email a été envoyé avec succès
        if ((boolean) response.get("success")) {
            return "true";  // Retourne "true" si l'email a été envoyé avec succès
        } else {
            return "false";  // Retourne "false" en cas d'erreur
        }
    }


}
