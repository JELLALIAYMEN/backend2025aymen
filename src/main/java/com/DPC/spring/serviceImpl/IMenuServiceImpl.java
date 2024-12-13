package com.DPC.spring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DPC.spring.entities.Menu;
import com.DPC.spring.repositories.MenuRepository;
import com.DPC.spring.services.IMenuService;

@Service
public class IMenuServiceImpl implements IMenuService {
	@Autowired
	MenuRepository menurepos ; 
	
	public String Creer(Menu m ) {
		this.menurepos.save(m);
		return "true" ; 
	}
	 public List<Menu> afficher(){
		 return this.menurepos.findAll();
	 }
	 public String update(Menu menu) {
		 Menu m =this.menurepos.findById(menu.getId()).get(); 
		 m = this.menurepos.saveAndFlush(menu);
		 return "true" ; 
	 } 

}
