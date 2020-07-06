package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.model.CourbeLT;
import com.example.springsecuritypfe.repository.CourbeLTRepository;
import com.example.springsecuritypfe.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourbeLTServiceImpl implements CourbeLTService {
	
	DateUtil dateutil = new DateUtil() ;
	
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
	public List<CourbeLT> findByMaturite(Integer maturite) {
		return courbeRepository.findByMaturite(maturite);	
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
	
	@Override
	public List<CourbeLT> generateCourbeLT(String date) throws ParseException {
		
		log.info("Génération des courbes de taux court terme ...");
		
		log.info("Les courbes de taux court terme correspondants à la date "+date+":");

		List<CourbeBDT> bdtlist = courbebdtService.findByDate(date);
		
		List<CourbeLT> ltlist = new ArrayList<CourbeLT>();
				
		Collections.sort(bdtlist, new Comparator<CourbeBDT>() {
		    @Override
		    public int compare(CourbeBDT courbe1, CourbeBDT courbe2) {
		        return courbe1.getMaturite().compareTo(courbe2.getMaturite()) ;
		}});
		
		int maturitestcourbe = 0 ;
		
		CourbeLT firstelement = new CourbeLT() ;
		
		for (int i = 1; i < bdtlist.size()+1; i++) {	
			
			if( bdtlist.get(i).getMaturite() > 365 ) { maturitestcourbe = bdtlist.get(i-1).getMaturite(); break ; }	
		}

		firstelement.setDateCourbe(date); 

		firstelement.setMaturite(maturitestcourbe);

		Double tauxmonetaire = courbebdtService.findByMaturite(maturitestcourbe).get(0).getTmp();
		
		// transférer le taux monétaire en un taux actuarial
				
		Double tauxactuarial =   (Math.pow(1+(tauxmonetaire*(maturitestcourbe/360d)),356d/maturitestcourbe)-1);		
		
		firstelement.setTaux((double) Math.round(tauxactuarial * 1000) / 1000);
		
		ltlist.add(firstelement);
		
		try {
			
			for (int i = 1; i < bdtlist.size()+1; i++) {
				
				CourbeLT element = new CourbeLT() ;

				int maturite = dateutil.datesdifference(bdtlist.get(i-1).getDateEcheance(),bdtlist.get(i-1).getDateValeur());

				if(maturite > 365) {
					element.setDateCourbe(date);
					element.setTaux(bdtlist.get(i-1).getTmp());
					element.setMaturite(maturite);
					ltlist.add(element);				
				}	
			}
			
		} catch (Exception e) {
			
			log.error("Erreur lors de la génération des courbes de taux long terme");
			e.printStackTrace();
			
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
