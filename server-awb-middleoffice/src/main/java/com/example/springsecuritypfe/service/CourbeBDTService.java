package com.example.springsecuritypfe.service;

import java.util.List;

import com.example.springsecuritypfe.model.CourbeBDT;

public interface CourbeBDTService {
	
	CourbeBDT saveCourbe(CourbeBDT courbe);
	
	void saveCourbe(List<CourbeBDT> courbe);

    List<CourbeBDT> findAllBDT();
    
    List<CourbeBDT> findByDate(String date);

}
