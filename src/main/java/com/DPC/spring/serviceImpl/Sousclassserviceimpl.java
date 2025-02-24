package com.DPC.spring.serviceImpl;

import com.DPC.spring.entities.Sousclass;
import com.DPC.spring.repositories.Sousclassrep;
import com.DPC.spring.services.Sousclassservice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sousclassserviceimpl implements Sousclassservice {
    private final Sousclassrep sousclassRepository;

    public Sousclassserviceimpl(Sousclassrep sousclassRepository) {
        this.sousclassRepository = sousclassRepository;
    }

    @Override
    public Sousclass createSousclass(String nomssousclasse) {
        Sousclass sousclass = new Sousclass();
        sousclass.setNomssousclasse(nomssousclasse);
        return sousclassRepository.save(sousclass);
    }

    @Override
    public List<Sousclass> sousclasses() {
        return sousclassRepository.findAll();
    }


}
