package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.springsecuritypfe.exception.BusinessResourceException;
import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.model.CourbeST;
import com.example.springsecuritypfe.repository.CourbeSTRepository;
import com.example.springsecuritypfe.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourbeSTServiceImpl implements CourbeSTService {
	
	DateUtil dateutil = new DateUtil() ;
	
	@Autowired
	private CourbeSTRepository courbeRepository;
	
	@Autowired
	private CourbeBDTService courbebdtService;
	
	@Autowired
	private CourbeSTService courbestService;
	
	@Autowired
	private TMPJJService tauxService;

	@Override
	public CourbeST saveCourbe(CourbeST courbe) {
		return courbeRepository.save(courbe);
	}

	@Override
	public void saveCourbe(List<CourbeST> courbe) {
		courbeRepository.saveAll(courbe);		
		
	}

	@Override
	public List<CourbeST> findAllST() {
        return courbeRepository.findAll();
	}

	@Override
	public List<CourbeST> findByMaturite(Integer maturite) {
		return courbeRepository.findByMaturite(maturite);	
	}
	
	@Override
	public List<CourbeST> findByDate(String date) throws ParseException {
		List<CourbeST> courbelist = courbeRepository.findByDateCourbe(date);
		if (!courbelist.isEmpty()) {
			log.info("Les courbes de taux court terme qui correspondent à la date "+date+" existent déjà en base de données.");
			log.info("Récupération de la base de données ...");
			return courbelist;
		} else {
			return generateCourbeST(date);
			
		}
	}
	
	@Override
	public List<CourbeST> generateCourbeST(String date)  throws BusinessResourceException {
		
		log.info("Génération des courbes de taux court terme ...");
		
		List<CourbeBDT> bdtlist = courbebdtService.findByDate(date);
		
		int maturiteltcourbe = 0 ;
		
		Collections.sort(bdtlist, new Comparator<CourbeBDT>() {
		    @Override
		    public int compare(CourbeBDT courbe1, CourbeBDT courbe2) {
		        return courbe1.getMaturite().compareTo(courbe2.getMaturite()) ;
		}});
			
		for (int i = 0; i < bdtlist.size()+1; i++) {	
			if( bdtlist.get(i).getMaturite() > 365 ) { maturiteltcourbe = bdtlist.get(i).getMaturite(); break ; }	
		}
		
		List<CourbeST> stlist = new ArrayList<CourbeST>();
		
		CourbeST lastelement = new CourbeST() ;
		
		log.info("Les courbes de taux court terme correspondants à la date "+date+":");
		
		try {	
			for (int i = 0; i < bdtlist.size()+1; i++) {
				
				CourbeST element = new CourbeST() ;
				
				if(i==0) {
					String tmp = tauxService.getTMP((bdtlist.get(i).getDateCourbe())).replace(",", ".");
					
					if(tmp=="" && dateutil.datesdifference(dateutil.datetostring(new Date()), date)<10L) {
						System.out.println("la date est supérieur à 22-06-2020");
						throw new BusinessResourceException("NoConnection", "votre connexion internet est instable", HttpStatus.REQUEST_TIMEOUT);
					}

					if(dateutil.datesdifference(dateutil.datetostring(new Date()), date)>10L) {
						System.out.println("la date est inférieur à 22-06-2020");
					}
					element.setDateCourbe(date);
					if(tmp=="") { element.setTaux(null);}
					else { element.setTaux(Double.parseDouble(tmp)); }
					element.setMaturite(1);
					stlist.add(i, element);
					
					//log.info("first element "+element.getDateCourbe()+" | "+element.getTaux()+" | "+element.getMaturite());	
				}
				else {	
					int maturite = dateutil.datesdifference(bdtlist.get(i-1).getDateEcheance(),bdtlist.get(i-1).getDateValeur());
					if(maturite<365) {
						element.setDateCourbe((bdtlist.get(i-1).getDateCourbe()));
						element.setTaux(bdtlist.get(i-1).getTmp());;
						element.setMaturite(maturite);
						stlist.add(i, element);
						
					//log.info(element.getDateCourbe()+" | "+element.getTaux()+" | "+element.getMaturite());	
					}
				}
			}
			
			lastelement.setDateCourbe(date);
			
			lastelement.setMaturite(maturiteltcourbe);

			// transférer le taux actuarial en un taux monétaire
			
			Double tauxactuarial = courbebdtService.findByMaturite(maturiteltcourbe).get(0).getTmp();
					
			Double tauxmonetaire = 	(Math.pow(1d+tauxactuarial,(double)maturiteltcourbe/365L)-1)*(360d/maturiteltcourbe);

			//log.info("taux actuarial : " + tauxactuarial);

			//log.info("maturite : " + maturiteltcourbe);
			
			//log.info("taux monetaire: " + (double) Math.round(tauxmonetaire * 1000) / 1000);

			lastelement.setTaux((double) Math.round(tauxmonetaire * 1000) / 1000);
			
			stlist.add(lastelement);
			
			//log.info("last element "+lastelement.getDateCourbe()+" | "+lastelement.getTaux()+" | "+lastelement.getMaturite());

		} catch (Exception e) {
			
			log.error("Erreur lors de la génération des courbes de taux court terme ");
			e.printStackTrace();
		}
		
		if(stlist.size()==0) {

			log.info("La liste courbe de taux court terme est vide.");
		}
		
		else {
			
			courbestService.saveCourbe(stlist);
			
			log.info("Les courbes de taux court terme correspondants à la date "+date+" sont bien enregistrées dans la base de données.");
		
		}		
		
		return stlist;		
	}
}
