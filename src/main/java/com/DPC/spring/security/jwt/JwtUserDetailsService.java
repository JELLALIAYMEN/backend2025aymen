package com.DPC.spring.security.jwt;


import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.AuthorityRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email.trim().isEmpty()) {
            throw new UsernameNotFoundException("username is empty");
        }

        Utilisateur user = userService.findByEmail(email);

        if (user==null) {
            throw new UsernameNotFoundException("User with email = " + email + " not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user));
    }
@Autowired
AuthorityRepository authrepos ;
    private List<GrantedAuthority> getGrantedAuthorities(Utilisateur user) {
    	 List<GrantedAuthority> authorities = new ArrayList<>(); 
    	 Autority auth = this.authrepos.findByName(user.getProfil());
    	     authorities.add(new SimpleGrantedAuthority(auth.getName())); 
    	     return authorities; }
}
