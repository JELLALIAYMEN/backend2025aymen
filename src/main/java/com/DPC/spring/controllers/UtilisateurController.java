package com.DPC.spring.controllers;

import com.DPC.spring.entities.ErrorResponse;
import com.DPC.spring.services.IUtilisateurService;
import com.DPC.spring.services.MailService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Classe;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.AuthorityRepository;
import com.DPC.spring.repositories.ClasseRepository;
import com.DPC.spring.repositories.UtilisateurRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;


//@CrossOrigin(origins = "http://localhost:4200") // Autorise uniquement Angular

@RestController
@RequestMapping("users")
@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

	@Autowired
	IUtilisateurService UtilisateurServiceImp;



	@DeleteMapping("/delete/{idUtilisateur}")
	@ApiOperation(value = "supprimer Utilisateur ")
	public void deleteUtilisateur(@PathVariable long idUtilisateur)  {
		UtilisateurServiceImp.deleteUtilisateur(idUtilisateur);
	}

	@PutMapping("/update/{id}")
	public void updateUser(@RequestBody Utilisateur utilisateur, @PathVariable long id) {
		UtilisateurServiceImp.updateUtilisateur(utilisateur,id);
	}
	@PutMapping("/update")
	@ResponseBody
	//@ApiOperation(value = " update personne ")
	public void updatePersonne(@RequestBody Utilisateur utilisateur) {
		UtilisateurServiceImp.updateUtilisateur(utilisateur);
	}

	//@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/GetAllUser")
	public List<Utilisateur> GetUtilisateur() {
		return UtilisateurServiceImp.GetUtilisateur();
	}


	//security login hhhh
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	UtilisateurRepository userrepos ;
	@Autowired
	AuthorityRepository authrepos ;
	@Autowired
	MailService mailservice ;
	
	@PostMapping("/envoyeremail")
	public String email(String email) throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.mailservice.RenisialiserMotdepasse(email);
		return "true";
	}
	
	@Autowired
	ClasseRepository classerepos ;
	@RequestMapping(value = "/addEleve", method = RequestMethod.POST)
	public String Ajout(@RequestBody Utilisateur user, String nomclasse) {
		Utilisateur userexist = this.userrepos.findByEmail(user.getEmail());
		if(userexist==null){
		Classe c = this.classerepos.findByNomclasse(nomclasse);
		Autority auth = this.authrepos.findByName(user.getProfil());
		String pass = encoder.encode(user.getPassword());
		user.setAuthority(auth);
		user.setPassword(pass);
		user.setDatecreation(new Date(System.currentTimeMillis()));	
		user.setArchiver(false);
		user.setClasse(c);
	 this.userrepos.save(user);
		return "true";
		}
		else{
			return  "user exist";
		}
	}
/*
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String AjoutParent(@RequestBody Utilisateur user) {
		Utilisateur userexist = this.userrepos.findByEmail(user.getEmail());
		if(userexist==null){
			Autority auth = this.authrepos.findByName(user.getProfil());
			String pass = encoder.encode(user.getPassword());
			user.setAuthority(auth);
			user.setPassword(pass);
			user.setDatecreation(new Date(System.currentTimeMillis()));
			user.setArchiver(false);
			this.userrepos.save(user);
			return "true";
		}
		else{
			return  "user exist";
		}
	}
*/
	
	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/admintest", method = RequestMethod.GET)
	public String testconnectadmin() {
		return "profil admin";
	}

	@RequestMapping(value = "/archiver", method = RequestMethod.POST)
	public String archiver(Long id) {
		Utilisateur u =this.userrepos.findById(id).get();
		u.setArchiver(true);
		this.userrepos.saveAndFlush(u);
		return "true";
	}
	@GetMapping("/getbyid/{id}")
	public Utilisateur getbyid(@PathVariable Long id) {
		Utilisateur u =this.userrepos.findById(id).get();
		return u ;
	}
			@GetMapping("/getbyemail")
			public Utilisateur userbyemail(@RequestParam String email) {
			Utilisateur u = this.userrepos.findByEmail(email);
				return u ;
	}
			@PostMapping("/changemp")
			public Utilisateur changemp(@RequestParam Long id,@RequestParam String password) {
				Utilisateur u = this.userrepos.findById(id).get();
				String pass = encoder.encode(password);
				u.setPassword(pass);
				this.userrepos.saveAndFlush(u);
				return u ;
	}

	@GetMapping("/countuser")
	public Long countuser() {
	return this.userrepos.countuser();}
	@GetMapping("/countmembre")
	public Long countmembre() {
	return this.userrepos.countmembre();}
	@GetMapping("/countjoueur")
	public Long countjoueur() {
	return this.userrepos.countjoueur();}
	@GetMapping("/countdoc")
	public Long countdoc() {
	return this.userrepos.countdocument();}

	@GetMapping("/count-eleves")
	public Long countEleves() {
		return UtilisateurServiceImp.countEleves();
	}

	@GetMapping("/count-enseignants")
	public Long countEnseignants() {
		return UtilisateurServiceImp.countEnseignants();
	}

	@PostMapping("/update")
	public String update(@RequestBody Utilisateur user ) {

		Utilisateur u =this.userrepos.findById(user.getId()).get();

		user.setDatecreation(u.getDatecreation());
		user.setAuthority(u.getAuthority());

		user.setArchiver(u.getArchiver());
		user.setPassword(u.getPassword());
		u=this.userrepos.save(user);
		return "true";

	}
	@GetMapping("/par-profil")
	public List<Utilisateur> findAllByProfil(@RequestParam String profil) {
		// Appeler le service pour récupérer tous les utilisateurs avec le profil donné
		return UtilisateurServiceImp.findAllByProfil(profil);
	}


	@PostMapping("/affecter/{email}/{id}")
	public Utilisateur affecterUtilisateurClasse(@PathVariable String email, @PathVariable Long id) {
		// Appel de la méthode pour affecter l'utilisateur
		Utilisateur utilisateur = UtilisateurServiceImp.affecterUtilisateurClasse(email, id);

		if (utilisateur != null) {
			// Si l'utilisateur a été affecté avec succès, le renvoyer
			return utilisateur;
		} else {
			// Si l'utilisateur n'a pas été trouvé ou affecté, renvoyer null ou une autre valeur
			return null;
		}
	}

	@GetMapping("/eleveByClass")
	public ResponseEntity<List<Utilisateur>> getElevesByClass(@RequestParam String nomclasse) {
		List<Utilisateur> eleves = userrepos.findByClasseNomclasse(nomclasse);
		return ResponseEntity.ok(eleves);
	}




/*
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String Ajoutuser(@RequestBody Utilisateur user) {
		// Vérifier si l'email existe déjà
		Utilisateur userexist = this.userrepos.findByEmail(user.getEmail());
		if (userexist != null) {
			return "user exist";
		}

		// Vérifier si le profil existe
		Autority auth = this.authrepos.findByName(user.getProfil());
		if (auth == null) {
			return "Profil introuvable";
		}

		// Encoder le mot de passe
		String pass = encoder.encode(user.getPassword());
		user.setAuthority(auth);
		user.setPassword(pass);
		user.setDatecreation(new Date(System.currentTimeMillis()));
		user.setArchiver(false);

		// Sauvegarder l'utilisateur
		this.userrepos.save(user);
		return "true";
	}
*/
@RequestMapping(value = "/addUser", method = RequestMethod.POST)
public ResponseEntity<?> Ajoutuser(@RequestBody Utilisateur user) {
	// Vérifier si l'email existe déjà
	Utilisateur userexist = this.userrepos.findByEmail(user.getEmail());
	if (userexist != null) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User already exists"));
	}

	// Vérifier si le profil existe
	Autority auth = this.authrepos.findByName(user.getProfil());
	if (auth == null) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Profil introuvable"));
	}

	// Encoder le mot de passe
	String pass = encoder.encode(user.getPassword());
	user.setAuthority(auth);
	user.setPassword(pass);
	user.setDatecreation(new Date(System.currentTimeMillis()));
	user.setArchiver(false);

	// Sauvegarder l'utilisateur
	this.userrepos.save(user);
	return ResponseEntity.ok("Utilisateur ajouté avec succès");
}





}
