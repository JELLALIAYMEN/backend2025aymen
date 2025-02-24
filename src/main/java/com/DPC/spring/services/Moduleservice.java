package com.DPC.spring.services;

import com.DPC.spring.DTO.MatiereDTO;
import com.DPC.spring.DTO.Moduledto;
import com.DPC.spring.entities.Menu;
import com.DPC.spring.entities.Module;

import java.util.Date;
import java.util.List;

public interface Moduleservice {
    void deleteById(Long id);

    public  String save(Module module,String matricule );



        // Method to update a module by its ID
        Module updateModule(Long id, Module updatedModule);

    List<Module> getModulesByMatricule(String matricule);
}