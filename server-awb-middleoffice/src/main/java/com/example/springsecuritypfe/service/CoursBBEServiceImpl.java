package com.example.springsecuritypfe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springsecuritypfe.model.CoursBBE;
import com.example.springsecuritypfe.repository.CoursBBERepository;
import com.example.springsecuritypfe.util.APIUtils;


@Service
public class CoursBBEServiceImpl implements CoursBBEService {
	
	@Autowired
	private CoursBBERepository coursRepository;
	
	
	private APIUtils bam;
	
	
	@Override
	public CoursBBE saveCours(CoursBBE cours) {
		return coursRepository.save(cours);		
	}
	
	@Override
	public void saveCours(List<CoursBBE> cours) {
		coursRepository.saveAll(cours);		
	}

	@Override
	public List<CoursBBE> findAllBBE() {
        return coursRepository.findAll();
	}
	

	@Override
	public List<CoursBBE> findByDate(String date) {
		
		List<CoursBBE> courslist = coursRepository.findByDate(date);
		if (!courslist.isEmpty()) {
			System.out.println("Cocou BBE");
			return courslist;
		} else {
			return bam.callgetlistbbe(date);
		}
	}   
		

    public Iterable<CoursBBE> list() {
        return coursRepository.findAll();
    }


    public Iterable<CoursBBE> save(List<CoursBBE> listcoursbbe) {
    	return coursRepository.save(listcoursbbe);
    }
	 
}
