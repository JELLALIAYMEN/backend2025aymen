package com.DPC.spring.controllers;

import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.LoginRequest;
import com.DPC.spring.entities.LoginResponse;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UtilisateurRepository userrepos;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login2")
    public ResponseEntity<LoginResponse> login2(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("Tentative de connexion avec l'email : " + loginRequest.getEmail());

        Utilisateur contact = this.userrepos.findByEmail(loginRequest.getEmail());

        if (contact == null) {
            System.out.println("Erreur : Utilisateur non trouvé !");
            return ResponseEntity.status(401).body(new LoginResponse(null, null, "Error: User not found", null, null, null));
        }

        System.out.println("Utilisateur trouvé : " + contact.getEmail());

        // Vérification du mot de passe
        if (!passwordEncoder.matches(loginRequest.getPassword(), contact.getPassword())) {
            System.out.println("Erreur : Mot de passe incorrect !");
            return ResponseEntity.status(401).body(new LoginResponse(null, null, "Error: Incorrect password", null, null, null));
        }

        // Authentification
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Récupérer les détails de l'utilisateur
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Authentification réussie pour : " + userDetails.getUsername());

        // Génération du token
        String token = this.jwtTokenUtils.generateToken(userDetails);
        System.out.println("Token généré : " + token);

        if (token == null || token.isEmpty()) {
            System.out.println("Erreur : Token non généré !");
            return ResponseEntity.status(500).body(new LoginResponse(null, null, "Error: Token generation failed", null, null, null));
        }

        // Récupérer le rôle
        String role = "No Role";
        Autority authority = contact.getAuthority();

        if (authority != null && authority.getName() != null) {
            role = authority.getName();
        } else {
            System.out.println("Aucune autorité trouvée pour l'utilisateur !");
        }

        System.out.println("Utilisateur connecté avec rôle : " + role);

        return ResponseEntity.ok(new LoginResponse(token, "Bearer", "Login successfully", role, loginRequest.getEmail(), contact.getMatricule()));
    }
}
