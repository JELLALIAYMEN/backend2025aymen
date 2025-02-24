package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Homework;

public interface HomeworkRepository extends JpaRepository<Homework,Long> {

}
