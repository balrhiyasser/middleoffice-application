package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.repository.CourbeBDTRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourbeBDTServiceImpl implements CourbeBDTService {
	
	@Autowired
	private CourbeBDTRepository courbeRepository;
	
	@Autowired
	private CourbeBDTService courbebdtService;

	
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
	public List<CourbeBDT> findByMaturite(Long maturite) {
		return courbeRepository.findByMaturite(maturite);
	}
	
	@Override
	public List<CourbeBDT> findByDate(String date) {
		
		List<CourbeBDT> courbelist = courbeRepository.findByDateCourbe(date);
		if (!courbelist.isEmpty()) {
			log.info("Les courbes de taux correspondant à la date "+date+" existent déjà dans la base de données.");
			return courbelist;
		} else {
			return callgetlistbdt(date);
			
		}
	}
	
	public List<CourbeBDT> callgetlistbdt(String date) {
		
		List<CourbeBDT> list = new ArrayList<CourbeBDT>();
		
		log.info("Récupération des courbes de taux");
		log.info("Appel à l'API Bank Al Maghrib ... ");

		try {
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);	
		    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		      
	        headers.set("Ocp-Apim-Subscription-Key", "1c0c5ec3c92740a38eec34f17db19101");
	        
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
			String url = String.format("https://api.centralbankofmorocco.ma/mo/Version1/api/CourbeBDT?dateCourbe=%s", date);
			RestTemplate restTemplate = new RestTemplate();
			
			list = Arrays.asList(restTemplate.exchange(url, HttpMethod.GET, entity, CourbeBDT[].class).getBody());
					
			courbebdtService.saveCourbe(list);
			
			log.info("Les courbes de taux correspondants à la date "+date+" sont bien enregistrées dans la base de données.");

		} catch (Exception e) {
			
			log.info("Erreur lors de l'appel à l'API Bank Al Maghrib"+e);

		}
		
		return list ;
		
	}
	
	
	@Override
	public List<CourbeBDT> generateBDT(List<CourbeBDT> res) throws ParseException {
		
		for (CourbeBDT courbeBDT : res) {

    		Date dateEcheance=new SimpleDateFormat("yyyy-MM-dd").parse(courbeBDT.getDateEcheance());
    		
    		Date dateValeur=new SimpleDateFormat("yyyy-MM-dd").parse(courbeBDT.getDateValeur());

    		DateTime dt1 = new DateTime(dateEcheance);
    		
    		DateTime dt2 = new DateTime(dateValeur);
    			    	
    		courbeBDT.setMaturite((long)Days.daysBetween(dt2, dt1).getDays());
    		
		}
		
		courbebdtService.saveCourbe(res);
		
    	log.info("les courbes de taux sont bien générées.");
    	
    	return res;
	}

	
	
	
}
