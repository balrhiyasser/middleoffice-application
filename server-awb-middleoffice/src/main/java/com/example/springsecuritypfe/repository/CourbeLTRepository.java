package com.example.springsecuritypfe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.CourbeLT;

public interface CourbeLTRepository extends JpaRepository<CourbeLT, Long>  {
	
	List<CourbeLT> findByDateCourbe(String dateCourbe);
	
	List<CourbeLT> findByMaturite(Integer maturite);

	List<CourbeLT> save(List<CourbeLT> listcourbebdt);

}
