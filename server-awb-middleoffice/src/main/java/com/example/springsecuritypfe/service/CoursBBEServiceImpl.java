package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springsecuritypfe.model.CoursBBE;
import com.example.springsecuritypfe.model.Parameter;
import com.example.springsecuritypfe.repository.CoursBBERepository;
import com.example.springsecuritypfe.util.APIUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoursBBEServiceImpl implements CoursBBEService {
	
	APIUtil apiutil = new APIUtil();
	
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
	public List<CoursBBE> findOnlyByDate(String date) {
		return coursRepository.findByDate(date);
	}
 
	
	@Override
	public List<CoursBBE> findByDate(String date) {
		
		List<CoursBBE> courslist = coursRepository.findByDate(date);
		if (!courslist.isEmpty()) {
			log.info("Les cours de billet correspondant à la date "+date+" existent déjà dans la base de données.");
			return courslist;
		} else {
			courslist=  apiutil.callgetlistbbe(date, parameterService.findByCle("KEY_BBE").getValeur(), parameterService.findByCle("URL_BBE").getValeur());
			coursbbeService.saveCours(courslist);
			log.info("Les cours de billet de banque correspondants à la date "+date+" sont bien enregistrées dans la base de données.");
			return courslist ;

		}
		
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

	
}
