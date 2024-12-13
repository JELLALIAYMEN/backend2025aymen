package com.DPC.spring.services;

import java.util.List;

import com.DPC.spring.entities.Menu;

public interface IMenuService {
	public String Creer(Menu m ) ;
	List<Menu> afficher();
	String update(Menu menu);
}
