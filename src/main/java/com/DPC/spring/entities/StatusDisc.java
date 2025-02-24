package com.DPC.spring.entities;

public enum StatusDisc {
    PENDING,        // Discipline en attente d'approbation
    APPROVED,       // Discipline approuvée
    REJECTED,       // Discipline rejetée
    PENDING_APPROVAL, //envoyer a l'admin
    VALIDATED,      // Discipline validée par l'administration
    COMPLETED,      // Traitement de la discipline terminé
    IN_PROGRESS,    // Discipline en cours de traitement
    ESCALATED       // Discipline escaladée à une autorité supérieure
}
