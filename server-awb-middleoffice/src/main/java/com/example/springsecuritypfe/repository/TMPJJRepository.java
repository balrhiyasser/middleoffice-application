package com.example.springsecuritypfe.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.TauxMPJJ;


public interface TMPJJRepository extends JpaRepository<TauxMPJJ, Long>  {
	
	TauxMPJJ findByDateTaux(String dateTaux);

}