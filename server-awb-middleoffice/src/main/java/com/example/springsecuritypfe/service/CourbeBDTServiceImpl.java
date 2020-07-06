package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.repository.CourbeBDTRepository;
import com.example.springsecuritypfe.util.APIUtil;
import com.example.springsecuritypfe.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourbeBDTServiceImpl implements CourbeBDTService {
	
	APIUtil apiutil = new APIUtil() ;
	
	DateUtil dateutil = new DateUtil() ;
	
	@Autowired
	private CourbeBDTRepository courbeRepository;
	
	@Autowired
	private CourbeBDTService courbebdtService;
	
	@Autowired
	private ParameterService parameterService;
	
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
	public List<CourbeBDT> findOnlyByDate(String date) {
		return courbeRepository.findByDateCourbe(date);
	}
	
	@Override
	public List<CourbeBDT> findByMaturite(Integer maturite) {
		return courbeRepository.findByMaturite(maturite);
	}
	
	@Override
	public List<CourbeBDT> findByDate(String date) {
		
		List<CourbeBDT> courbelist = courbeRepository.findByDateCourbe(date);
		if (!courbelist.isEmpty()) {
			log.info("Les courbes de taux correspondant à la date "+date+" existent déjà dans la base de données.");
			return courbelist;
		} else {
			courbelist= apiutil.callgetlistbdt(date, parameterService.findByCle("KEY_BDT").getValeur(), parameterService.findByCle("URL_BDT").getValeur()); 
			courbebdtService.saveCourbe(courbelist);
			log.info("Les courbes de taux correspondants à la date "+date+" sont bien enregistrées dans la base de données.");
			return courbelist ;
			
		}
	}
	
	@Override
	public List<CourbeBDT> generateBDT(List<CourbeBDT> res) throws ParseException {
		
		for (CourbeBDT courbeBDT : res) {		
			courbeBDT.setMaturite(dateutil.datesdifference(courbeBDT.getDateEcheance(), courbeBDT.getDateValeur()));
		}
		
		courbebdtService.saveCourbe(res);
		
    	log.info("les courbes de taux sont bien générées.");
    	
    	return res;
	}

	

	
}
