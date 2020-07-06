package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.List;

import com.example.springsecuritypfe.model.CourbeLT;

public interface CourbeLTService {
	
	CourbeLT saveCourbe(CourbeLT courbe);
	
	void saveCourbe(List<CourbeLT> courbe);

    List<CourbeLT> findAllLT();
    
    List<CourbeLT> findByDate(String date) throws ParseException;
    
    List<CourbeLT> findByMaturite(Integer maturite);
    
    List<CourbeLT> generateCourbeLT(String date) throws ParseException;
    


}
