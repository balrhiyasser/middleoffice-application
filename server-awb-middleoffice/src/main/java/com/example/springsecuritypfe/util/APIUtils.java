package com.example.springsecuritypfe.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.model.CoursBBE;
import com.example.springsecuritypfe.model.Parameter;
import com.example.springsecuritypfe.service.CourbeBDTService;
import com.example.springsecuritypfe.service.CoursBBEService;
import com.example.springsecuritypfe.service.ParameterService;

public class APIUtils {
	
	@Autowired
	private CoursBBEService coursbbeService;
	
	@Autowired
	private CourbeBDTService courbebdtService;
	
	@Autowired
	private ParameterService parameterService;
	
	
	
	public List<CoursBBE> callgetlistbbe(String date) {
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);	
		    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		      
	        // example of custom header
	        headers.set("Ocp-Apim-Subscription-Key", "b012767065a84ae3a106f1f138f125d6");
	        
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
			
			String url = String.format("https://api.centralbankofmorocco.ma/cours/Version1/api/CoursBBE?date=%s", date);
			
			RestTemplate restTemplate = new RestTemplate();
			
			Parameter commissionOfficeChange = parameterService.findByCle("commissionOfficeChange");
			
			Parameter commisionAWB = parameterService.findByCle("commisionAWB");
			
			Parameter commisionAWBDelegataire = parameterService.findByCle("commisionAWBDelegataire");
			
			List<CoursBBE> list = Arrays.asList(restTemplate.exchange(url, HttpMethod.GET, entity, CoursBBE[].class).getBody());
			
			for (CoursBBE coursBBE : list) {
				
				
				coursBBE.setMidBAM(coursBBE.getAchatClientele(), coursBBE.getVenteClientele());
				coursBBE.setVenteinter(coursBBE.getMidBAM());
				coursBBE.setAchatinterBAM(coursBBE.getMidBAM(),commissionOfficeChange);
				coursBBE.setVenteinterBAM(coursBBE.getMidBAM(),commissionOfficeChange);
				coursBBE.setAchatClienteleCAL(coursBBE.getMidBAM(),commisionAWB);
				coursBBE.setVenteClienteleCAL(coursBBE.getMidBAM(),commisionAWB);
				coursBBE.setRachatinter(coursBBE.getAchatinterBAM());
				coursBBE.setRachatsousdel(coursBBE.getMidBAM(),commisionAWBDelegataire);
	
				}
			
			coursbbeService.saveCours(list);
			
			System.out.println("Cours BBE Saved !");
			
			
			return list ;
			
		}
	
	public List<CourbeBDT> callgetlistbdt(String date) {
		
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);	
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	      
        // example of custom header
        headers.set("Ocp-Apim-Subscription-Key", "5280373b87154b549fd35ccb537842ab");
        
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
	    
		String url = String.format("https://api.centralbankofmorocco.ma/mo/Version1/api/CourbeBDT?dateCourbe=%s", date);
		
		RestTemplate restTemplate = new RestTemplate();
		
		
		List<CourbeBDT> list = Arrays.asList(restTemplate.exchange(url, HttpMethod.GET, entity, CourbeBDT[].class).getBody());
		
		
		courbebdtService.saveCourbe(list);
		
		System.out.println("Cours BDT Saved !");
		
		return list ;
		
	}

}

