package com.example.springsecuritypfe.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsecuritypfe.model.CourbeBDT;
import com.example.springsecuritypfe.model.CourbeST;
import com.example.springsecuritypfe.repository.CourbeSTRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourbeSTServiceImpl implements CourbeSTService {
	
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
	public List<CourbeST> findByMaturite(Long maturite) {
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
	
	 Long GetMaturite(String DateEcheance, String DateValeur) throws ParseException {
		
		Date dateEcheance=new SimpleDateFormat("yyyy-MM-dd").parse(DateEcheance);
			
		Date dateValeur=new SimpleDateFormat("yyyy-MM-dd").parse(DateValeur);

		DateTime dt1 = new DateTime(dateEcheance);
		
		DateTime dt2 = new DateTime(dateValeur);
					    			
		return (long)Days.daysBetween(dt2, dt1).getDays() ;
		
	}
	
	@Override
	public List<CourbeST> generateCourbeST(String date) throws ParseException {
		
		log.info("Génération des courbes de taux court terme ...");
		
		List<CourbeBDT> bdtlist = courbebdtService.findByDate(date);
		
		Long maturiteltcourbe = 0L ;
		
		Collections.sort(bdtlist, new Comparator<CourbeBDT>() {
		    @Override
		    public int compare(CourbeBDT courbe1, CourbeBDT courbe2) {
		        return courbe1.getMaturite().compareTo(courbe2.getMaturite()) ;
		}});
			
		for (int i = 0; i < bdtlist.size()+1; i++) {	
			
			if( bdtlist.get(i).getMaturite() > 365 ) {
				maturiteltcourbe = bdtlist.get(i).getMaturite(); break ; 
			}	
		}
		
		List<CourbeST> stlist = new ArrayList<CourbeST>();
		
		CourbeST lastelement = new CourbeST() ;
		
		log.info("Les courbes de taux court terme correspondants à la date "+date+":");
		
		try {	
			for (int i = 0; i < bdtlist.size()+1; i++) {
				
				CourbeST element = new CourbeST() ;
				
				if(i==0) {
					
					String tmp = tauxService.getTMP((bdtlist.get(i).getDateCourbe())).replace(",", ".");
					element.setDateCourbe(date);
					if(tmp=="") { element.setTaux(null);}
					else { element.setTaux(Double.parseDouble(tmp)); }
					element.setMaturite(1L);
					stlist.add(i, element);
					
					//log.info("first element "+element.getDateCourbe()+" | "+element.getTaux()+" | "+element.getMaturite());	
				}
				else {	
					long maturite = GetMaturite(bdtlist.get(i-1).getDateEcheance(),bdtlist.get(i-1).getDateValeur());
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

	@Override
	public String getTMP(String date) {
		
		String dateformatted = date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4) ;
		
		final String url = "http://www.bkam.ma/Marches/Principaux-indicateurs/Marche-monetaire/Marche-monetaire-interbancaire";
		
        String taux = "" ;
        
        try {

            final Document document = Jsoup.connect(url).proxy(null).get();
            
            for (Element row : document.select("table.dynamic_contents_ref_5 tr")) {
            	
            	if(row.select("td:nth-of-type(1)").text().equals(dateformatted)) {	
            		taux = taux+row.select("td:nth-of-type(2)").text().substring(0,5);
            		System.out.println(taux);
            		break;
            	}
            }
        }
            
        catch (Exception ex) {
        	
        	ex.printStackTrace();
        	
        }
        
        System.out.println("Taux moyen pondéré récupéré pour le jour : "+dateformatted+" est : "+ taux);
        
		return taux;
	}
	
}
