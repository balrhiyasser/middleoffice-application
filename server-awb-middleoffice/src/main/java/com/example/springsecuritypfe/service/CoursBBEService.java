package com.example.springsecuritypfe.service;

import java.util.List;
import com.example.springsecuritypfe.model.CoursBBE;


public interface CoursBBEService {
	
	
	CoursBBE saveCours(CoursBBE cours);
	
	void saveCours(List<CoursBBE> cours);

    List<CoursBBE> findAllBBE();
    
    List<CoursBBE> findByDate(String date);



	
	
	

}

