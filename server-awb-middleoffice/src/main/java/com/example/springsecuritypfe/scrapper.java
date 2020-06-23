package com.example.springsecuritypfe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class scrapper {

	public static void main(String[] args) {

			final String url = "http://www.bkam.ma/Marches/Principaux-indicateurs/Marche-monetaire/Marche-monetaire-interbancaire";
	        
	        try {

	        	
	            final Document document = Jsoup.connect(url).proxy(null).get();
	            
	            for (Element row : document.select("table.dynamic_contents_ref_5 tr")) {
	            	
	            	if(row.select("td:nth-of-type(1)").text().equals("11/06/2020")) {
	            		
	            		System.out.println(row.select("td:nth-of-type(2)").text().replace("%", " "));
	            	}

                }
	        }
	            
	        catch (Exception ex) {
	        	
	        }
		}
	}


	/*@Override
	public Long getTMP(String date) {
		
		final String url = "http://www.bkam.ma/Marches/Principaux-indicateurs/Marche-monetaire/Marche-monetaire-interbancaire";
	    
	    try {
	        final Document document = Jsoup.connect(url).get();
	        
	        for (Element row : document.select("table.dynamic_contents_ref_5 tr")) {
	        
	        	if (row.select("td:nth-of-type(1)").text().equals("")) {
	                continue;
	            }
	            else {
	            	
	            	
	            	                   
	               final String date = 
	                       row.select("td:nth-of-type(1)").text();
	               
	               final String dateformatted = date.substring(6, 10)+"-"+date.substring(3, 5)+"-"+date.substring(0, 2);
	               
	               final String taux = 
	                       row.select("td:nth-of-type(2)").text().replace("%", "");
	
	               System.out.println(date+" "+taux+" "+dateformatted);
	            }
	        }
	    }
	        
	    catch (Exception ex) {
	        ex.printStackTrace();
	    }
		
		
	}*/
