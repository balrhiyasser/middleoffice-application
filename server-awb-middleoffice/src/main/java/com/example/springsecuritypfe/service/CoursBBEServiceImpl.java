package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.springsecuritypfe.model.CoursBBE;
import com.example.springsecuritypfe.model.Parameter;
import com.example.springsecuritypfe.repository.CoursBBERepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoursBBEServiceImpl implements CoursBBEService {
	
	@Autowired
	private CoursBBERepository coursRepository;
	
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private CoursBBEService coursbbeService;
	
	
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
			log.info("Les cours de billet correspondant à la date "+date+" existent déjà dans la base de données.");
			return courslist;
		} else {
			return callgetlistbbe(date);
		}
	}
	
	public List<CoursBBE> callgetlistbbe(String date) {
		
		List<CoursBBE> list = new ArrayList<CoursBBE>();
		
		log.info("Récupération des cours de billet de banque");
		log.info("Appel à l'API Bank Al Maghrib ... ");
		
		try {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);	
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	      
        headers.set("Ocp-Apim-Subscription-Key", "b012767065a84ae3a106f1f138f125d6");
        
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = String.format("https://api.centralbankofmorocco.ma/cours/Version1/api/CoursBBE?date=%s", date);
		
		RestTemplate restTemplate = new RestTemplate();
		
		list = Arrays.asList(restTemplate.exchange(url, HttpMethod.GET, entity, CoursBBE[].class).getBody());
		
		coursbbeService.saveCours(list);
		
		log.info("Les cours de billet de banque correspondants à la date "+date+" sont bien enregistrées dans la base de données.");
		
		} catch (Exception e) {
			
			log.info("Erreur lors de l'appel à l'API Bank Al Maghrib"+e);

		}
		
		return list ;
		
	}
	
	@Override
	public List<CoursBBE> generateBBE(List<CoursBBE> res) throws ParseException {
		
		Parameter commissionOfficeChange = parameterService.findByCle("commissionOfficeChange");
		
		Parameter commisionAWB = parameterService.findByCle("commisionAWB");
		
		Parameter commisionAWBDelegataire = parameterService.findByCle("commisionAWBDelegataire");
		
		for (CoursBBE coursBBE : res) {
			
			coursBBE.setMidBAM(coursBBE.getAchatClientele(), coursBBE.getVenteClientele());
			coursBBE.setVenteinter(coursBBE.getMidBAM());
			coursBBE.setAchatinterBAM(coursBBE.getMidBAM(),commissionOfficeChange);
			coursBBE.setVenteinterBAM(coursBBE.getMidBAM(),commissionOfficeChange);
			coursBBE.setAchatClienteleCAL(coursBBE.getMidBAM(),commisionAWB);
			coursBBE.setVenteClienteleCAL(coursBBE.getMidBAM(),commisionAWB);
			coursBBE.setRachatinter(coursBBE.getAchatinterBAM());
			coursBBE.setRachatsousdel(coursBBE.getMidBAM(),commisionAWBDelegataire);

		}
		
		coursbbeService.saveCours(res);
		
    	log.info("Les cours de billet de banque sont bien générées.");
    	
    	return res;
	}
		

    public Iterable<CoursBBE> list() {
        return coursRepository.findAll();
    }


    public Iterable<CoursBBE> save(List<CoursBBE> listcoursbbe) {
    	return coursRepository.save(listcoursbbe);
    }
	 
}
