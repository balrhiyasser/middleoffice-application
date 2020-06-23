package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.model.CourbeLT;
import com.example.springsecuritypfe.repository.CourbeLTRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourbeLTServiceImpl implements CourbeLTService {
	
	@Autowired
	private CourbeLTRepository courbeRepository;
	
	@Autowired
	private CourbeBDTService courbebdtService;
	
	@Autowired
	private CourbeLTService courbeltService;

	@Override
	public CourbeLT saveCourbe(CourbeLT courbe) {
		return courbeRepository.save(courbe);
	}

	@Override
	public void saveCourbe(List<CourbeLT> courbe) {
		courbeRepository.saveAll(courbe);		
		
	}

	@Override
	public List<CourbeLT> findAllLT() {
        return courbeRepository.findAll();
	}

	@Override
	public List<CourbeLT> findByDate(String date) throws ParseException {
		
		List<CourbeLT> courbelist = courbeRepository.findByDateCourbe(date);
		
		if (!courbelist.isEmpty()) {
			log.info("Les courbes de taux long terme qui correspondent à la date "+date+" existent déjà en base de données.");
			log.info("Récupération de la base de données ...");
			return courbelist;
		} else {
			return generateCourbeLT(date);
			
		}
	}	
	
	public Long GetMaturity(String DateEcheance, String DateValeur) throws ParseException {
		
		long dateEcheance=new SimpleDateFormat("yyyy-MM-dd").parse(DateEcheance).getTime();
		long dateValeur=new SimpleDateFormat("yyyy-MM-dd").parse(DateValeur).getTime();
		return (dateEcheance-dateValeur)/(1000*60*60) ;
		
	}
	

	@Override
	public List<CourbeLT> generateCourbeLT(String date) throws ParseException {
		
		log.info("Génération des courbes de taux court terme ...");
		
		log.info("Les courbes de taux court terme correspondants à la date "+date+":");

		
		List<CourbeBDT> bdtlist = courbebdtService.findByDate(date);
				
		List<CourbeLT> ltlist = new ArrayList<CourbeLT>();
		
		try {
			
			for (int i = 0; i < bdtlist.size(); i++) {
				
				CourbeLT element = new CourbeLT() ;

				
				long maturite = GetMaturity(bdtlist.get(i).getDateEcheance(),bdtlist.get(i).getDateValeur());

				if(maturite>365) {
					element.setDateCourbe((bdtlist.get(i).getDateCourbe()));
					element.setTaux(bdtlist.get(i).getTmp());
					element.setMaturite(maturite);
					ltlist.add(i, element);
					
					log.info(element.getDateCourbe()+" | "+element.getTaux()+" | "+element.getMaturite());
				}
	
			}
			
		} catch (Exception e) {
			log.error("Erreur lors de la génération des courbes de taux long terme"+e);
		}

		
		if(ltlist.isEmpty()) {
			log.info("La liste courbe de taux long terme est vide.");
		}
		else {
			
			courbeltService.saveCourbe(ltlist);
			
			log.info("Les courbes de taux long terme correspondants à la date "+date+" sont bien enregistrées dans la base de données.");

		}
		
		return ltlist;
	}

	
}
