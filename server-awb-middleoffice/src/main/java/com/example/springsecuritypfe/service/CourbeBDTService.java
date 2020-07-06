package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.List;

import com.example.springsecuritypfe.model.CourbeBDT;

public interface CourbeBDTService {
	
	CourbeBDT saveCourbe(CourbeBDT courbe);
	
	void saveCourbe(List<CourbeBDT> courbe);

    List<CourbeBDT> findAllBDT();
    
    List<CourbeBDT> findByDate(String date);
    
    List<CourbeBDT> findOnlyByDate(String date);

    List<CourbeBDT> findByMaturite(Integer Maturite) ;
        
    List<CourbeBDT> generateBDT(List<CourbeBDT> list) throws ParseException;
    
     
    
}
