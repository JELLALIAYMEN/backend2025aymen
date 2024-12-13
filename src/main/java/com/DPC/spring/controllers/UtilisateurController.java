package com.DPC.spring.controllers;

import com.DPC.spring.services.IUtilisateurService;
import com.DPC.spring.services.MailService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import javax.crypto.NoSuchPaddingException;


@CrossOrigin("*")
@RestController
@RequestMapping("users")
public class UtilisateurController {

	// crud  Utilisateur

	@Autowired
     private IUtilisateurService iUtilisateurService;

	
	@DeleteMapping("/delete")
	@ApiOperation(value = "supprimer Utilisateur ")
	public void deleteUtilisateur(long idUtilisateur)  {

		iUtilisateurService.deleteUtilisateur(idUtilisateur);
	}

	@PutMapping("/update")
	@ResponseBody
	//@ApiOperation(value = " update personne ")
	public void updatePersonne(@RequestBody Utilisateur utilisateur) {
		iUtilisateurService.updateUtilisateur(utilisateur);
	}

	@GetMapping("/GetAllUser")
	public List<Utilisateur> GetUtilisateur() {
		return iUtilisateurService.GetUtilisateur();
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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String Ajout(@RequestBody Utilisateur user, String nomclasse) {
		Utilisateur userexist = this.userrepos.findByEmail(user.getEmail());
		if(userexist==null){
		Classe c = this.classerepos.findByNomclasse(nomclasse);
		Autority auth = this.authrepos.findByName(user.getProfil());
		String pass = encoder.encode(user.getPassword());
		user.setAuthorities(auth);
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


	
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String adduser(@RequestBody Utilisateur user) {
		Utilisateur userexist = this.userrepos.findByEmail(user.getEmail());
		if(userexist==null){
		Autority auth = this.authrepos.findByName(user.getProfil());
		String pass = encoder.encode(user.getPassword());
		user.setAuthorities(auth);
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
@GetMapping("/getbyid")
public Utilisateur getbyid(Long id) {
	Utilisateur u =this.userrepos.findById(id).get();
	return u ;
}
		@GetMapping("/getbyemail")
		public Utilisateur userbyemail(String email) {
			Utilisateur u = this.userrepos.findByEmail(email);
			return u ;
}
		@PostMapping("/changemp")
		public Utilisateur changemp(Long id,String password) {
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



@PostMapping("/update")
public String update(@RequestBody Utilisateur user ) {
	
	Utilisateur u =this.userrepos.findById(user.getId()).get();
	Autority auth = this.authrepos.findByName(user.getProfil());
	user.setAuthorities(auth);

	user.setDatecreation(u.getDatecreation());
	user.setAuthorities(u.getAuthorities());
	user.setArchiver(u.getArchiver());
	user.setPassword(u.getPassword());
	u=this.userrepos.save(user);
	return "true";

}


@PostMapping("/updateEleve")
public String updateEleve(@RequestBody Utilisateur user , String nomclasse) {
	Classe c = this.classerepos.findByNomclasse(nomclasse);
	
	Utilisateur u =this.userrepos.findById(user.getId()).get();
	Autority auth = this.authrepos.findByName(user.getProfil());
	user.setAuthorities(auth);
	user.setClasse(c);

	user.setDatecreation(u.getDatecreation());
	user.setAuthorities(u.getAuthorities());
	user.setArchiver(u.getArchiver());
	user.setPassword(u.getPassword());
	u=this.userrepos.save(user);
	return "true";

}


@PostMapping("/updateeleve")
public String update(@RequestBody Utilisateur user,String nomclasse ) {
	Classe c = this.classerepos.findByNomclasse(nomclasse);

	Utilisateur u =this.userrepos.findById(user.getId()).get();
	user.setClasse(c);
	user.setDatecreation(u.getDatecreation());
	user.setAuthorities(u.getAuthorities());
	user.setArchiver(u.getArchiver());
	user.setPassword(u.getPassword());
	u=this.userrepos.save(user);
	return "true";

}

	// bech nbadel el template fel front
	// bech nriglou login admin
	// zid champ matricule w les champ ne9ssin
	// crud user lezem iwali yemchy w el interface mte3hom
	// profil el chef w mateb3ou
	// crud congee w mateb3ou
}
