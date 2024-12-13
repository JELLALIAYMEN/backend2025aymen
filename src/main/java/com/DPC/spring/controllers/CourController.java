package com.DPC.spring.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Cours;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.CoursRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

@RestController
@RequestMapping("cours")
@CrossOrigin("*")
public class CourController {
@Autowired
CoursRepository courrrepos ;
@Autowired
UtilisateurRepository userrepos ;
@Autowired
ClasseRepository classrepos ; 
@PostMapping("/ajouterRapport")
public String ajouterfichier(@RequestPart("fichier") MultipartFile file, String nomcour, String email , String nomclasse)
		throws IOException {
Utilisateur u = this.userrepos.findByEmail(email);
Classe c = this.classrepos.findByNomclasse(nomclasse);

byte[] compressedFileBytes = compressBytes(file.getBytes());
	
Cours cour = new Cours(null, nomcour, compressedFileBytes, file.getOriginalFilename(), file.getContentType(), u, c);
courrrepos.save(cour);
//creer un date d'aujourd'hui
	return "true";
}	
// decompresser fichier pdf
public static byte[] decompressBytes(byte[] data) {
    if (data == null || data.length == 0) {
        System.out.println("Data is null or empty.");
        return new byte[0];
    }

    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
    } catch (IOException ioe) {
        System.err.println("IOException during decompression: " + ioe.getMessage());
    } catch (DataFormatException e) {
        System.err.println("DataFormatException during decompression: " + e.getMessage());
    }
    return outputStream.toByteArray();
}
public static byte[] compressBytes(byte[] data) throws IOException {
    if (data == null || data.length == 0) {
        throw new IllegalArgumentException("Data is empty or null.");
    }

    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];

    while (!deflater.finished()) {
	    int count = deflater.deflate(buffer);
	    outputStream.write(buffer, 0, count);
	}

    return outputStream.toByteArray();
}
@GetMapping("all")
public List<Cours> all(){
	return this.courrrepos.findAll();
}
@GetMapping("allbyeleve")

public List<Cours> allbyuser(String email){
	Utilisateur u = this.userrepos.findByEmail(email);
	Classe c = this.classrepos.findByNomclasse(u.getClasse().getNomclasse());
	return this.courrrepos.findByClasse(c);
}


@GetMapping("allbyprof")

public List<Cours> allbyprof(String email){
	Utilisateur u = this.userrepos.findByEmail(email);
	return this.courrrepos.findByUser(u);
}

@GetMapping("pdf")
public ResponseEntity<byte[]> getPdfById(Long id) throws IOException, DataFormatException {
    Cours post = courrrepos.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    byte[] pdfBytes = decompressBytes(post.getPicByte());

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=" + post.getId() + ".pdf");
    headers.setContentType(MediaType.APPLICATION_PDF);
    //headers.setContentType(MediaType.parseMediaType("application/pdf"));

    return ResponseEntity.ok().headers(headers).body(pdfBytes);

}



@GetMapping("pdfbyid")
public Cours pdfbyid(Long id){
	return this.courrrepos.findById(id).get();
}



}
