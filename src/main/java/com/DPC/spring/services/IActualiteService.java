package com.DPC.spring.services;

import com.DPC.spring.entities.Actualite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IActualiteService {
    Actualite createActualite(Actualite actualite, MultipartFile video, MultipartFile fichier) throws IOException;
    Page<Actualite> getAllActualites(int page, int size);
    Page<Actualite> getActualitesByCible(String profil, int page, int size);
    Actualite getActualiteById(Long id);
}
