package com.example.springsecuritypfe.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.CoursBBE;


public interface CoursBBERepository extends JpaRepository<CoursBBE, Long> {
	
    List<CoursBBE> findByDate(String date);

	List<CoursBBE> save(List<CoursBBE> listcoursbbe);
    

}
