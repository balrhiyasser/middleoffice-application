package com.example.springsecuritypfe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecuritypfe.export.ExcelFileExporter;
import com.example.springsecuritypfe.export.FileExporter;
import com.example.springsecuritypfe.service.CourbeLTService;
import com.example.springsecuritypfe.service.CourbeSTService;
import com.example.springsecuritypfe.service.CoursBBEService;
import com.example.springsecuritypfe.service.TMPJJService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DownloadController {
	
	
    @Autowired
    private CoursBBEService coursbbeService;
    
    @Autowired
    private CourbeLTService courbeltService;
    
    @Autowired
    private CourbeSTService courbestService;
    
    @Autowired
    private TMPJJService tauxService;
	
	
	@GetMapping("/download/coursbbeexcel")
    public void downloadExcel(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
		
        try {
        	log.info("Téléchargement du fichier Excel Cours de Billet "+date+" en cours ...");
        	response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=coursbbe.xlsx");
            ByteArrayInputStream stream = ExcelFileExporter.coursListToExcelFile(coursbbeService.findByDate(date));
            IOUtils.copy(stream, response.getOutputStream());
            log.info("Le fichier Excel Cours de Billet est bien téléchargé.");
            
		} catch (Exception e) {
			log.error("Erreur lors du téléchargement du fichier ! "+e);
		}
        
    }
    
    @GetMapping("/download/coursbbecsv")
    public void downloadCsv(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
    	
        try {
        	log.info("Téléchargement du fichier CSV Cours de Billet "+date+" en cours ...");
        	response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment");
            FileExporter.downloadCsv(response.getWriter(),coursbbeService.findByDate(date)) ;
            log.info("Le fichier CSV Cours de Billet est bien téléchargé.");
            
		} catch (Exception e) {
			log.error("Erreur lors du téléchargement du fichier ! "+e);
		}
        
    }
    
    @GetMapping("/download/BAMFX03")
    public void downloadTxtbam(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
    	
        try {
        	log.info("Téléchargement du fichier TXT BAMFX03 "+date+" en cours ...");
            response.setContentType("text/txt");
            response.setHeader("Content-Disposition", "attachment; file=BAMFX03.txt");
            FileExporter.downloadTxtbam(response.getWriter(), coursbbeService.findByDate(date)) ;
            log.info("Le fichier TXT BAMFX03 est bien téléchargé.");
            
		} catch (Exception e) {
			log.error("Erreur lors du téléchargement du fichier ! "+e);
		}
        
    }
    
    @GetMapping("/download/WAFACASH")
    public void downloadTxtwafa(HttpServletResponse response, @RequestParam("date") String date) throws IOException {
        try {
        	log.info("Téléchargement du fichier TXT WAFACASH "+date+" en cours ...");
            response.setContentType("text/txt");
            response.setHeader("Content-Disposition", "attachment; file=WAFACASH.txt ");
            FileExporter.downloadTxtwafa(response.getWriter(), coursbbeService.findByDate(date)) ;
            log.info("Le fichier TXT WAFACASH est bien téléchargé.");
		} catch (Exception e) {
			log.error("Erreur lors du téléchargement du fichier ! "+e);
		}
    }
    
    @GetMapping("/download/shorterm")
    public void downloadSTCsv(HttpServletResponse response, @RequestParam("date") String dateCourbe) throws IOException, ParseException {
    	
        try {
        	log.info("Téléchargement du fichier CSV Courbe de Taux Short Term "+dateCourbe+" en cours ...");
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment");
            FileExporter.downloadSTCsv(response.getWriter(),courbestService.findByDate(dateCourbe),dateCourbe) ;
            log.info("Le fichier CSV Courbe de Taux Short Term est bien téléchargé.");
            
		} catch (Exception e) {
			log.error("Erreur lors du téléchargement du fichier ! "+e);
		}
        
    }
    
    @GetMapping("/download/longterm")
    public void downloadLTCsv(HttpServletResponse response, @RequestParam("date") String dateCourbe) throws IOException, ParseException {
    	
        try {
        	log.info("Téléchargement du fichier CSV Courbe de Taux Long Term "+dateCourbe+" en cours ...");
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment");
            FileExporter.downloadLTCsv(response.getWriter(),courbeltService.findByDate(dateCourbe),dateCourbe) ;
            log.info("Le fichier CSV Courbe de Taux Long Term est bien téléchargé.");

		} catch (Exception e) {
			log.error("Erreur lors du téléchargement du fichier ! "+e);
		}
        
    }
    
    @GetMapping("/download/tmp")
    public void downloadTMPJJCsv(HttpServletResponse response, @RequestParam("date") String dateTaux) throws IOException, ParseException {
    	
        try {
        	log.info("Téléchargement du fichier CSV Taux Moyen Pondéré "+dateTaux+" en cours ...");
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment");
            FileExporter.downloadTMPJJCsv(response.getWriter(),tauxService.findByDate(dateTaux),dateTaux) ;
            log.info("Le fichier CSV Taux Moyen Pondéré est bien téléchargé.");

		} catch (Exception e) {
			log.error("Erreur lors du téléchargement du fichier ! "+e);
		}
        
    }

}
