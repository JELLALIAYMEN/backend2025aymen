package com.DPC.spring.services;

import java.util.List;

import com.DPC.spring.DTO.MatiereDTO;
import com.DPC.spring.DTO.NoteDTO;
import com.DPC.spring.entities.Trimestre;

public interface Inoteservice {

    List<NoteDTO> findByEl_IdAndTrimestreAndMat_Id(Long elId, Trimestre trimestre, Long idMatiere);





    NoteDTO save(NoteDTO noteDTO);

    MatiereDTO saveMatiere(MatiereDTO matiereDTO);


}