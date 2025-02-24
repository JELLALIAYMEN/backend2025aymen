package com.DPC.spring.serviceImpl;

import com.DPC.spring.entities.Discipline;
import com.DPC.spring.entities.StatusDisc;
import com.DPC.spring.entities.TypeDisc;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.DisciplineRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.IDisciplineService;
import com.DPC.spring.services.IParentEleveService;
import com.DPC.spring.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DisciplineServiceImp implements IDisciplineService {
    private static final String UPLOAD_DIRECTORY = "C:/uploads/videos/"; // Chemin du dossier local pour stocker les vidéos
    @Autowired
    DisciplineRepository disciplineRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    IParentEleveService iParentEleveService;

    @Override
    public Discipline ajouterDiscipline(MultipartFile file, LocalDate date, String cause, Long eleveId, Long enseignantId) throws IOException {
        // Vérifier si le fichier est non vide
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier vidéo est requis.");
        }

        // Sauvegarder la vidéo localement
        String fileName = file.getOriginalFilename();
        String filePath = UPLOAD_DIRECTORY + fileName;

        // Créer le dossier si nécessaire
        File directory = new File(UPLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Sauvegarder le fichier
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Récupérer les utilisateurs (élève et enseignant)
        Utilisateur eleve = utilisateurRepository.findById(eleveId)
                .orElseThrow(() -> new IllegalArgumentException("Élève introuvable avec l'ID " + eleveId));
        Utilisateur enseignant = utilisateurRepository.findById(enseignantId)
                .orElseThrow(() -> new IllegalArgumentException("Enseignant introuvable avec l'ID " + enseignantId));

        // Créer et sauvegarder la discipline
        Discipline discipline = new Discipline();
        discipline.setDate(date);
        discipline.setCause(cause);
        discipline.setVideoPath(filePath); // Sauvegarder uniquement le chemin
        discipline.setEleve(eleve);
        discipline.setEnseignant(enseignant);
        discipline.setStatusDisc(StatusDisc.PENDING);

        return disciplineRepository.save(discipline);
    }

    @Override
    public Discipline getDisciplineById(Long id) {
        return disciplineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discipline introuvable avec l'ID " + id));
    }

    // Lister toutes les disciplines d'un enseignant
    @Override
    public List<Discipline> listDisciplinesByEnseignant(Long enseignantId) {
        Utilisateur enseignant = utilisateurRepository.findById(enseignantId).orElse(null);
        return disciplineRepository.findByEnseignant(enseignant);
    }

    @Override
    public List<Discipline> getDisciplinesByStudentId(Long eleveId) {
        Utilisateur eleve = utilisateurRepository.findById(eleveId).orElse(null);
        return disciplineRepository.findByEleveAndStatusDiscOrderByDateDesc(eleve, StatusDisc.APPROVED);
    }

    // Modifier une discipline
    @Override
    public Discipline updateDiscipline(Long id, String cause, LocalDate date, MultipartFile file) throws IOException {
        Discipline discipline = getDisciplineById(id);
        discipline.setCause(cause);
        discipline.setDate(date);

        if (file != null) {
            String newFilePath = saveFile(file);
            discipline.setVideoPath(newFilePath);
        }

        return disciplineRepository.save(discipline);
    }

    @Override
    public void deleteDiscipline(Long id) {
        disciplineRepository.deleteById(id);
    }

    // Changer le statut d'une discipline
    @Override
    public Discipline sendDisciplinetoAdmin(Long id) {
        // Récupérer la discipline par son ID
        Discipline discipline = getDisciplineById(id);
        Utilisateur admin = utilisateurRepository.findByProfil("admin");
        discipline.setStatusDisc(StatusDisc.PENDING_APPROVAL);

        mailService.sendEmailToAdmin(discipline, admin.getEmail(), discipline.getEnseignant().getEmail());
        return disciplineRepository.save(discipline);
    }

    @Override
    public Discipline validerOuRefuser(Long id, String status, String adminComment) {
        Discipline discipline = getDisciplineById(id);
        StatusDisc newStatus = StatusDisc.valueOf(status);
        List<String> parentEmails = iParentEleveService.getParentEmails(discipline.getEleve().getEmail()); // Emails des parents
        Utilisateur admin = utilisateurRepository.findByProfil("admin");
        discipline.setStatusDisc(newStatus);
        discipline.setAdminComment(adminComment);
        disciplineRepository.save(discipline);

        if (newStatus == StatusDisc.APPROVED) {
            // Notification à l'élève
            mailService.envoyerNotificationDisciplineAleleve(discipline.getEleve().getEmail(), admin.getEmail(), discipline.getCause(), adminComment);
            // Notification aux parents
            for (String parentEmail : parentEmails) {
                mailService.envoyerNotificationDisciplineToParent(discipline.getEleve(), parentEmail, discipline.getCause(), discipline.getDate(), admin.getEmail(), adminComment);
            }
        }
        if(newStatus == StatusDisc.REJECTED) {
            mailService.envoyerNotificationDisciplineAEneseignant(discipline.getEleve(), discipline.getEnseignant().getEmail(),adminComment);
        }
        return null;
    }

    @Override
    public List<Discipline> listDisciplines() {
        List<Discipline> allDisciplines = disciplineRepository.findAllByOrderByDateDesc();
        return allDisciplines;
    }


    // Sauvegarder le fichier
    private String saveFile(MultipartFile file) throws IOException {
        String folderPath = "C:/uploaded_videos/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(folderPath + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return path.toString();
    }
}
