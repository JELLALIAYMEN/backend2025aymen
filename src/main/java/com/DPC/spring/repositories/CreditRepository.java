package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DPC.spring.entities.Credit;
import com.DPC.spring.entities.Utilisateur;

public interface CreditRepository extends JpaRepository<Credit,Long> {

	List<Credit> findByArchiverIsFalse();

	List<Credit> findByUser(Utilisateur u);
	@Query(nativeQuery=true,value="select * from credit where user_id in(select id from utilisateur where classe_id=:id)and archiver =0")
	List<Credit> creditbyclasse(Long id );


}
