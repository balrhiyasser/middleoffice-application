package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.List;

import com.example.springsecuritypfe.exception.BusinessResourceException;
import com.example.springsecuritypfe.model.CourbeST;

public interface CourbeSTService {
	
	CourbeST saveCourbe(CourbeST courbe);
	
	void saveCourbe(List<CourbeST> courbe);

    List<CourbeST> findAllST();
    
    List<CourbeST> findByMaturite(Integer maturite);

    List<CourbeST> findByDate(String date) throws ParseException;
    
    List<CourbeST> generateCourbeST(String date) throws BusinessResourceException;

}
