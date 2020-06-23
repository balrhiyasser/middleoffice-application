package com.example.springsecuritypfe.service;

import java.util.List;

import com.example.springsecuritypfe.model.TauxMPJJ;

public interface TMPJJService {
	
	TauxMPJJ saveTaux(TauxMPJJ taux);
	
    List<TauxMPJJ> findAllTMPJJ();
    
    TauxMPJJ findByDate(String date);
            
    String getTMP(String date) ;


}
