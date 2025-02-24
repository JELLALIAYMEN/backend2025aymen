package com.DPC.spring.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.DPC.spring.entities.Emploidetemps;
import com.DPC.spring.entities.Menu;
import com.DPC.spring.entities.TypeMenu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.NoSuchPaddingException;
import javax.persistence.*;

public interface IMenuService {
	//public String Creer(Menu m ) ;
	List<Menu> afficher();
	String Creermenu(Menu m  ,String  platentree,String  platprincipale,String dessert,String email ,TypeMenu  typeMenu ) throws NoSuchAlgorithmException, NoSuchPaddingException ;
	public ResponseEntity<String> updateMenu( Long id,  Menu updatedMenu);
}
