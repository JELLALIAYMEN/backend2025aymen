package com.DPC.spring.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Menu;
import com.DPC.spring.repositories.MenuRepository;
import com.DPC.spring.services.IMenuService;

@RestController
@RequestMapping("menu")
@CrossOrigin("*")
public class MenuControler {
@Autowired
IMenuService service  ;
@Autowired
MenuRepository menurepos ; 
@PostMapping("/creer")
public String Creer(@RequestBody Menu m ) {
	return this.service.Creer(m);
}
@GetMapping("/afficher")
public List<Menu> afficher(){
	 return this.service.afficher();
}

@GetMapping("afficherbydate")
public List<Menu> searchMenus(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    return this.menurepos.findMenusByDate(date);
}
@PutMapping("update")
public String update(@RequestBody Menu menu) {
	return this.service.update( menu);
}
@GetMapping("afficherbyid")
public Menu afficherbyid(Long id ) {
	return this.menurepos.findById(id).get(); 
}
}
