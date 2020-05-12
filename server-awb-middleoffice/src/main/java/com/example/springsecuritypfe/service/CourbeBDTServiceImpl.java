package com.example.springsecuritypfe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.repository.CourbeBDTRepository;
import com.example.springsecuritypfe.util.APIUtils;

@Service
public class CourbeBDTServiceImpl implements CourbeBDTService {
	
	@Autowired
	private CourbeBDTRepository courbeRepository;
	
	private APIUtils bam;
	
	@Override
	public CourbeBDT saveCourbe(CourbeBDT courbe) {
		return courbeRepository.save(courbe);		
	}
	
	@Override
	public void saveCourbe(List<CourbeBDT> cours) {
		courbeRepository.saveAll(cours);		
	}

	@Override
	public List<CourbeBDT> findAllBDT() {
        return courbeRepository.findAll();
	}
	

	@Override
	public List<CourbeBDT> findByDate(String date) {
		
		List<CourbeBDT> courbelist = courbeRepository.findByDateCourbe(date);
		if (!courbelist.isEmpty()) {
			System.out.println("Cocou BDT");
			return courbelist;
		} else {
			return bam.callgetlistbdt(date);
		}
	}   
		

    public Iterable<CourbeBDT> list() {
        return courbeRepository.findAll();
    }


    public Iterable<CourbeBDT> save(List<CourbeBDT> listcourbebdt) {
    	return courbeRepository.save(listcourbebdt);
    }
    

}
