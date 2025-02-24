package com.DPC.spring.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DPC.spring.entities.Menu;

public interface MenuRepository extends JpaRepository<Menu,Long> {

	List<Menu> findByNomjour(String jour);

	//@Query("SELECT m FROM Menu m WHERE :currentDate BETWEEN m.datedeb AND m.datefin")
	//List<Menu> findMenusByDate(@Param("currentDate") Date currentDate);
}
