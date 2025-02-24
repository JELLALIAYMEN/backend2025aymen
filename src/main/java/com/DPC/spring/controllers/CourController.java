package com.DPC.spring.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.DPC.spring.entities.Matiere;
import com.DPC.spring.repositories.Matiererep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Cours;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.CoursRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
@CrossOrigin("*")
@RestController
@RequestMapping("cours")
public class CourController {
@Autowired
CoursRepository courrrepos ;
@Autowired
UtilisateurRepository userrepos ;
@Autowired
ClasseRepository classrepos ;
	@Autowired
	Matiererep matrepos ;
	@PostMapping("/ajouterRapport")
	public ResponseEntity<Map<String, String>> ajouterfichier(
			@RequestPart("fichier") MultipartFile file,
			@RequestParam("nomcour") String nomcour,
			@RequestParam("email") String email,
			@RequestParam("nomclasse") String nomclasse,
			@RequestParam("nomMatiere") String nomMatiere) throws IOException {

		Map<String, String> response = new HashMap<>();

		// Vérifications
		Utilisateur u = this.userrepos.findByEmail(email);
		if (u==null) {
			response.put("error", "Utilisateur introuvable avec l'email fourni");
			return ResponseEntity.badRequest().body(response);
		}

		Classe c = this.classrepos.findByNomclasse(nomclasse);
		if (c == null) {
			response.put("error", "Classe introuvable avec le nom fourni");
			return ResponseEntity.badRequest().body(response);
		}

		Matiere m = this.matrepos.findByNom(nomMatiere);
		if (c == null) {
			response.put("error", "Matiere introuvable avec le nom fourni");
			return ResponseEntity.badRequest().body(response);
		}

		if (file.isEmpty() || file.getOriginalFilename() == null || file.getContentType() == null) {
			response.put("error", "Fichier invalide");
			return ResponseEntity.badRequest().body(response);
		}

		// Création et sauvegarde de l'objet Cours
		Cours cour = new Cours(null, nomcour, file.getBytes(), file.getOriginalFilename(), file.getContentType(), u, c, m);
		courrrepos.save(cour);

		response.put("message", "Fichier ajouté avec succès");
		return ResponseEntity.ok(response);
	}
	@GetMapping("/by-email")
	public ResponseEntity<List<Cours>> getCoursesByUserEmail(@RequestParam String email) {
		List<Cours> courses = courrrepos.findByUserEmail(email);
		if (courses.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(courses);
		}
		return ResponseEntity.ok(courses);
	}
	@GetMapping("/classe/{classeId}")
	public List<Cours> getCoursByClasseId(@PathVariable Long classeId) {
		return courrrepos.findCoursByClasse(classeId);
	}
	// decompresser fichier pdf
public static byte[] decompressBytes(byte[] data) {
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
} catch (DataFormatException e) {
}
return outputStream.toByteArray();
}
	@GetMapping("/getFichierCours/{id}")
	public ResponseEntity<byte[]> getFichierCours(@PathVariable Long id) {
		Cours cours = courrrepos.findById(id).orElse(null);
		if (cours == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		// Récupérer le fichier depuis la base de données
		byte[] fichier = cours.getPicByte();
		// Retourner le fichier avec le bon type MIME
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(cours.getType()))
				.body(fichier);
	}

public static byte[] compressBytes(byte[] data) {
Deflater deflater = new Deflater();
deflater.setInput(data);
deflater.finish();

ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
byte[] buffer = new byte[1024];
while (!deflater.finished()) {
	int count = deflater.deflate(buffer);
	outputStream.write(buffer, 0, count);
}
try {
	outputStream.close();
} catch (IOException e) {
}
return outputStream.toByteArray();
}



}
