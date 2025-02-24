package com.DPC.spring.services;

import com.DPC.spring.entities.Module;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IParentEleveService {
     List<String> getParentEmails(String eleveEmail);
     public ResponseEntity<String> affecterTravail(
              String emailParent, String travail);
     public List<Module> getTravauxParParent(String emailParent);
}
