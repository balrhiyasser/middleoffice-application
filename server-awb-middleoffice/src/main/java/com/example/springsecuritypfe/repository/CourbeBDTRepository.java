package com.example.springsecuritypfe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.CourbeBDT;

public interface CourbeBDTRepository extends JpaRepository<CourbeBDT, Long>  {
	
	List<CourbeBDT> findByDateCourbe(String dateCourbe);
	
	List<CourbeBDT> findByMaturite(Long maturite);

	List<CourbeBDT> save(List<CourbeBDT> listcourbebdt);

}
