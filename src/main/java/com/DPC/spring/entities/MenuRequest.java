package com.DPC.spring.entities;

public class MenuRequest {
    private String platEntree;
    private String platPrincipal;
    private String dessert;
    private String email;
    private TypeMenu typeMenu;

    // Getters et Setters
    public String getPlatEntree() { return platEntree; }
    public void setPlatEntree(String platEntree) { this.platEntree = platEntree; }

    public String getPlatPrincipal() { return platPrincipal; }
    public void setPlatPrincipal(String platPrincipal) { this.platPrincipal = platPrincipal; }

    public String getDessert() { return dessert; }
    public void setDessert(String dessert) { this.dessert = dessert; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public TypeMenu getTypeMenu() { return typeMenu; }
    public void setTypeMenu(TypeMenu typeMenu) { this.typeMenu = typeMenu; }
}
