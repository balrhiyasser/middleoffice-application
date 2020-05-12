package com.example.springsecuritypfe.export;

import java.io.PrintWriter;

import java.util.List;

import com.example.springsecuritypfe.model.CoursBBE;

public class FileExporter {
	
	
	// Télécharger fichier CSV 
	
	public static void downloadCsv(PrintWriter writer, List<CoursBBE> coursList) {
		

        writer.write("Libelle Devise,Date,Unite,AchatClientele,VenteClientele,MidBAM,AchatClienteleCAL,VenteClienteleCAL,Rachatinter,Venteinter,Rachatsousdel,VenteinterBAM,AchatinterBAM \n");
        for (CoursBBE coursbbe : coursList) {
            writer.write(coursbbe.getLibDevise() + ";" + 
		       			 coursbbe.getDate() + ";" + 
		       			 coursbbe.getUniteDevise() + ";" +
		       			 coursbbe.getAchatClientele() + ";" +
		       			 coursbbe.getVenteClientele() + ";" +
		       			 coursbbe.getMidBAM() + ";" +
		       			 coursbbe.getAchatClienteleCAL() + ";" +
		       			 coursbbe.getVenteClienteleCAL() + ";" +
		       			 coursbbe.getRachatinter() + ";" + 
		       			 coursbbe.getVenteinter() + ";" + 
		       			 coursbbe.getRachatsousdel() + ";" +
		       			 coursbbe.getAchatinterBAM() + ";" + 
		       			 coursbbe.getVenteinterBAM() + "\n");
        }
    }
	
	
	// formatter la date 
	
		public static String formatdate(String oldate) {
			
		return oldate.substring(8, 10) + oldate.substring(5, 7)+ oldate.substring(0, 4) ; }
		
	
	// Télécharger fichier Text BAM (BAMFX03.txt)  

	
	public static void downloadTxtbam(PrintWriter writer, List<CoursBBE> coursList) {
		
        for (CoursBBE coursbbe : coursList) {
            writer.write(coursbbe.getLibDevise() + ";" + 
            		     formatdate(coursbbe.getDate()) + ";" + 
		       			 coursbbe.getUniteDevise() + ";" +
		       			 coursbbe.getAchatClienteleCAL() + ";" + 
            			 coursbbe.getVenteClientele() + ";" + 
            			 coursbbe.getRachatsousdel() + ";" +  
            			 coursbbe.getVenteinterBAM() + "\n");
        }
    }
	
	// Télécharger fichier Text BAM (WAFACASH.txt)  

	
	public static void downloadTxtwafa(PrintWriter writer, List<CoursBBE> coursList) {
		
        for (CoursBBE coursbbe : coursList) {
            writer.write(coursbbe.getLibDevise() + ";" + 
            			 formatdate(coursbbe.getDate()) + ";" + 
            			 coursbbe.getUniteDevise() + ";" + 
            			 coursbbe.getAchatClientele() + ";" + 
            			 coursbbe.getRachatinter() + ";" + 
            			 coursbbe.getVenteinter() + ";" + 
            			 coursbbe.getVenteClienteleCAL() + "\n");
        }
    }

}
