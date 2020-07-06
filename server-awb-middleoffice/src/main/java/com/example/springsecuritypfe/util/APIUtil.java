package com.example.springsecuritypfe.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.model.CoursBBE;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class APIUtil {

	public List<CourbeBDT> callgetlistbdt(String date, String key, String url) {
			
		List<CourbeBDT> list = new ArrayList<CourbeBDT>();
		
		log.info("Récupération des courbes de taux");
		
		log.info("Appel à l'API Bank Al Maghrib ... ");
		
		try {

			HttpHeaders headers = new HttpHeaders();
			
		    headers.setContentType(MediaType.APPLICATION_JSON);	
		    
		    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		    		    
	        headers.set("Ocp-Apim-Subscription-Key", key);
	        
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
		    
			RestTemplate restTemplate = new RestTemplate();
			
			list = Arrays.asList(restTemplate.exchange(url+date, HttpMethod.GET, entity, CourbeBDT[].class).getBody());

		} catch (Exception e) {
			
			log.info("Erreur lors de l'appel à l'API Bank Al Maghrib");
			
			e.printStackTrace();

		}
		
		return list ;
		
	}
	
	public List<CoursBBE> callgetlistbbe(String date, String key, String url) {
		
		List<CoursBBE> list = new ArrayList<CoursBBE>();
		
		log.info("Récupération des cours de billet de banque");
		
		log.info("Appel à l'API Bank Al Maghrib ... ");
		
		try {
		
		HttpHeaders headers = new HttpHeaders();
		
	    headers.setContentType(MediaType.APPLICATION_JSON);	
	    
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	      
        headers.set("Ocp-Apim-Subscription-Key", key);
        
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
				
		RestTemplate restTemplate = new RestTemplate();
		
		list = Arrays.asList(restTemplate.exchange(url+date, HttpMethod.GET, entity, CoursBBE[].class).getBody());
		
		} catch (Exception e) {
			
			log.info("Erreur lors de l'appel à l'API Bank Al Maghrib"+e);

		}
		
		return list ;
		
	}

}
