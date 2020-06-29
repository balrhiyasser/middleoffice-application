package com.example.springsecuritypfe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.CourbeST;

public interface CourbeSTRepository extends JpaRepository<CourbeST, Long>  {
	
	List<CourbeST> findByDateCourbe(String dateCourbe);
	
	List<CourbeST> findByMaturite(Long maturite);

	List<CourbeST> save(List<CourbeST> listcourbebdt);

}

