package com.DPC.spring.serviceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.DPC.spring.entities.TypeMenu;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.DPC.spring.entities.Menu;
import com.DPC.spring.repositories.MenuRepository;
import com.DPC.spring.services.IMenuService;

import javax.crypto.NoSuchPaddingException;

@Service
public class IMenuServiceImpl implements IMenuService {
	@Autowired
	MenuRepository menurepos ;
	@Autowired
	MailService mailservice ;

	@Autowired
	UtilisateurRepository userepos ;

	 public List<Menu> afficher(){
		 return this.menurepos.findAll();
	 }
	@Override
	public String Creermenu(Menu m, String platentree, String platprincipale, String dessert, String email, TypeMenu typeMenu)
			throws NoSuchAlgorithmException, NoSuchPaddingException {

	Utilisateur p = this.userepos.findByEmail(email);

		if (p==null) {
			return "Utilisateur non trouvé";
		}

		// Création et sauvegarde du menu
		Menu menu = new Menu();
		menu.setDessert(dessert);
		menu.setPlatentree(platentree);
		menu.setPlatprincipale(platprincipale);
		menu.setTypemenu(typeMenu);

		this.menurepos.save(menu);

		// Envoi de l'email
		Map<String, Boolean> emailStatus = this.mailservice.EnvoyerMenu(email, platentree, platprincipale, dessert, typeMenu);

		// Vérifier si l'envoi de l'email a réussi
		if (emailStatus.getOrDefault("response", false)) {
			return "Menu créé et email envoyé avec succès.";
		} else {
			return "Menu créé, mais échec de l'envoi de l'email.";
		}
	}
	@Override
	public ResponseEntity<String> updateMenu(Long id, Menu updatedMenu) {
		// Recherche du menu existant par son ID
		Menu menu = menurepos.findById(id).orElse(null);

		// Vérification si le menu existe
		if (menu != null) {
			// Mise à jour du menu existant
			menu.setPlatprincipale(updatedMenu.getPlatprincipale());
			menu.setPlatentree(updatedMenu.getPlatentree());
			menu.setDessert(updatedMenu.getDessert());
			menu.setNomjour(updatedMenu.getNomjour());

			// Sauvegarde du menu mis à jour
			menurepos.save(menu);

			// Retourne une réponse indiquant que la mise à jour a été réussie
			return ResponseEntity.ok("Menu mis à jour avec succès !");
		} else {
			// Retourne une réponse indiquant que le menu n'a pas été trouvé
			return ResponseEntity.status(404).body("Menu non trouvé !");
		}
	}


	 public String genererTableauHTML() {
	        StringBuilder html = new StringBuilder();
	        html.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
	        html.append("<tr><th>Jour</th><th>Entrée</th><th>Plat</th><th>Dessert</th></tr>");

	        List<String> jours = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi");

	        for (String jour : jours) {
	            List<Menu> menus = menurepos.findByNomjour(jour);
	            for (Menu menu : menus) {
	                html.append("<tr>")
	                    .append("<td>").append(menu.getNomjour()).append("</td>")
	                    .append("<td>").append(menu.getPlatentree()).append("</td>")
	                    .append("<td>").append(menu.getPlatprincipale()).append("</td>")
	                    .append("<td>").append(menu.getDessert()).append("</td>")
	                    .append("</tr>");
	            }
	        }

	        html.append("</table>");
	        return html.toString();
	    }
	}



