package com.example.springsecuritypfe.service;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springsecuritypfe.model.TauxMPJJ;
import com.example.springsecuritypfe.repository.TMPJJRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TMPJJServiceImpl implements TMPJJService {

	@Autowired
	private TMPJJRepository tauxRepository;
	
	@Autowired
	private TMPJJService courbetmpjjService;

	@Override
	public TauxMPJJ saveTaux(TauxMPJJ taux) {
		return tauxRepository.save(taux);
	}

	@Override
	public List<TauxMPJJ> findAllTMPJJ() {
        return tauxRepository.findAll();
	}

	@Override
	public TauxMPJJ findByDate(String date) {
		
		TauxMPJJ element = tauxRepository.findByDateTaux(date);
		TauxMPJJ newelement = new TauxMPJJ();

		if (element!=null) {
			log.info("Le taux moyen pondéré qui correspond à la date "+date+" existe déjà en base de données.");
			log.info("Récupération de la base de données ...");
			return element;
		} else {
			newelement.setDateTaux(date);
			newelement.setTaux(Double.parseDouble(getTMP(date).replace(",", ".")));
			courbetmpjjService.saveTaux(newelement);
			log.info("Le taux moyen pondéré correspondant à la date "+date+" est bien enregistrée dans la base de données.");
			return newelement ;
		}
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
