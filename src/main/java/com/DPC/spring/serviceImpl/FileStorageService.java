package com.DPC.spring.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;@Service
public class FileStorageService {
    @Value("${file.upload-dir-actualities}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        // Vérifie si le dossier existe, sinon le créer
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // Enregistre le fichier
        String filePath = uploadDir + "/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return filePath; // Retourne le chemin du fichier
    }
}


