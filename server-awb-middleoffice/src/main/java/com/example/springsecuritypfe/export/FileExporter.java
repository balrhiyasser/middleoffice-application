package com.example.springsecuritypfe.export;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.springsecuritypfe.model.CourbeLT;
import com.example.springsecuritypfe.model.CourbeST;
import com.example.springsecuritypfe.model.CoursBBE;
import com.example.springsecuritypfe.model.TauxMPJJ;
import com.example.springsecuritypfe.util.DateUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FileExporter {
	
	static DateUtil util = new DateUtil() ;
	
	static String separator = ";";
	
	// Télécharger fichier CSV 
	
	public static void downloadCsv(PrintWriter writer, List<CoursBBE> coursList) {
		

        writer.write("Libelle Devise,Date,Unite,AchatClientele,VenteClientele,MidBAM,AchatClienteleCAL,VenteClienteleCAL,Rachatinter,Venteinter,Rachatsousdel,VenteinterBAM,AchatinterBAM \n");
        for (CoursBBE coursbbe : coursList) {
            writer.write(coursbbe.getLibDevise() + separator + 
		       			 coursbbe.getDate() + separator + 
		       			 coursbbe.getUniteDevise() + separator +
		       			 coursbbe.getAchatClientele() + separator +
		       			 coursbbe.getVenteClientele() + separator +
		       			 coursbbe.getMidBAM() + separator +
		       			 coursbbe.getAchatClienteleCAL() + separator +
		       			 coursbbe.getVenteClienteleCAL() + separator +
		       			 coursbbe.getRachatinter() + separator + 
		       			 coursbbe.getVenteinter() + separator + 
		       			 coursbbe.getRachatsousdel() + separator +
		       			 coursbbe.getAchatinterBAM() + separator + 
		       			 coursbbe.getVenteinterBAM() + "\n");
        }
    }
	

	// Télécharger fichier Text BAM (BAMFX03.txt)  

	
	public static void downloadTxtbam(PrintWriter writer, List<CoursBBE> coursList) {
		
        for (CoursBBE coursbbe : coursList) {
            writer.write(coursbbe.getLibDevise() + separator + 
            		     util.formatdate(coursbbe.getDate()) + separator + 
		       			 coursbbe.getUniteDevise() + separator +
		       			 coursbbe.getAchatClienteleCAL() + separator + 
            			 coursbbe.getVenteClientele() + separator + 
            			 coursbbe.getRachatsousdel() + separator +  
            			 coursbbe.getVenteinterBAM() + "\n");
        }
    }
	
	// Télécharger fichier Text BAM (WAFACASH.txt)  

	
	public static void downloadTxtwafa(PrintWriter writer, List<CoursBBE> coursList) {
		
        for (CoursBBE coursbbe : coursList) {
            writer.write(coursbbe.getLibDevise() + separator + 
            			 util.formatdate(coursbbe.getDate()) + separator + 
            			 coursbbe.getUniteDevise() + separator + 
            			 coursbbe.getAchatClientele() + separator + 
            			 coursbbe.getRachatinter() + separator + 
            			 coursbbe.getVenteinter() + separator + 
            			 coursbbe.getVenteClienteleCAL() + "\n");
        }
    }
	
	// Télécharger fichier CSV Courbe Court Terme (COURBE_MS_ST_<date>.csv )  

	
	
	public static void downloadSTCsv(PrintWriter writer, List<CourbeST> coursSTList, String dateCourbe) {
		
		try {
			
			Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dateCourbe);  
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
			String strDate= formatter.format(date);
			writer.write("Date"+", "+strDate+"\n");
		    writer.write("Maturity, Taux \n");
		    for (CourbeST courbest : coursSTList) {
	            writer.write(courbest.getMaturite() + "," + courbest.getTaux() + "\n");
	        }
		    
		} catch (Exception e) {
			log.error("Erreur lors de l'extraction du fichier CSV courbe court terme ! " +e);
		}
     
	 }
	
	// Télécharger fichier CSV Courbe Long Terme (COURBE_MS_ST_<date>.csv )  

	
	
		public static void downloadLTCsv(PrintWriter writer, List<CourbeLT> coursLTList, String dateCourbe) {
			
			try {
				
				Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dateCourbe);  
				SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
				String strDate= formatter.format(date);
				writer.write("Date"+", "+strDate+"\n");
			    writer.write("Maturity, Taux \n");
			    for (CourbeLT courbelt : coursLTList) {
		            writer.write(courbelt.getMaturite() + "," + courbelt.getTaux() + "\n");
		        }
			    
			} catch (Exception e) {
				log.error("Erreur lors de l'extraction du fichier CSV long court terme ! " +e);
			}
	     
		 }
		
		
		
	// Télécharger fichier CSV Taux moyen pondéré (TMPJJ<date>.csv )  

	
	
		public static void downloadTMPJJCsv(PrintWriter writer, TauxMPJJ taux, String dateCourbe) {
			
			try {
				
				Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dateCourbe);  
				SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
				String strDate= formatter.format(date);
				writer.write("Date"+", "+strDate+"\n");
			    writer.write("Maturity, Taux \n");
			    writer.write(taux.getMaturite() + "," + taux.getTaux() + "\n");

			} catch (Exception e) {
				log.error("Erreur lors de l'extraction du fichier CSV Taux moyen pondéré ! " +e);
			}
	     
		 }
	
	
}
