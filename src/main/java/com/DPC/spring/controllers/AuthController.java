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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/*package com.DPC.spring.controllers;

import com.DPC.spring.entities.LoginRequest;
import com.DPC.spring.entities.LoginResponse;/*
package com.DPC.spring.controllers;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
/*
@CrossOrigin("*")
@RestController
@RequestMapping("auth")
public class AuthController {

   
    @Autowired
    UtilisateurRepository userrepos ;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @PostMapping("/login2")
    public ResponseEntity<LoginResponse> login2(@Valid @RequestBody LoginRequest loginRequest)
    {
	
    	
    	  Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

          SecurityContextHolder.getContext().setAuthentication(authentication);
          UserDetails userDetails = (UserDetails) authentication.getPrincipal();
          String token = this.jwtTokenUtils.generateToken(userDetails);
    	
		Utilisateur contact = this.userrepos.findByEmail(loginRequest.getEmail());
		System.out.println(contact);
		return ResponseEntity.ok(new LoginResponse(token, "Bearer", "Login successfully",contact.getAuthorities().getName(),loginRequest.getEmail()));
*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        Utilisateur contact = this.userrepos.findByEmail(loginRequest.getEmail());

        if (contact ==null) {
            return ResponseEntity.status(401).body(new LoginResponse(null, null, "Error: User not found", null, null,null));
        }

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = this.jwtTokenUtils.generateToken(userDetails);

        // Get authority information with null checks
        String role = "No Role"; // Default role if no authority is found

        // Retrieve the authority object
        Autority authority = contact.getAuthority();  // This retrieves the authority object

        if (authority != null && authority.getName() != null) {
            role = authority.getName();  // Assuming name of authority is the role
        } else {
            // Log or handle case where authority is missing
            role = "No Role";  // Default to "No Role"
        }

        // Return the login response with token and role information
        return ResponseEntity.ok(new LoginResponse(token, "Bearer", "Login successfully", role, loginRequest.getEmail(),contact.getMatricule()));
    }
}
