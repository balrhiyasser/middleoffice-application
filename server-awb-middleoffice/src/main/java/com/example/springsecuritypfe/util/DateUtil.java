package com.example.springsecuritypfe.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class DateUtil {
	
	// Différence entre deux dates
	
	public Integer datesdifference(String date1, String date2) throws ParseException {
				
		Date DateSup=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			
		Date DateInf=new SimpleDateFormat("yyyy-MM-dd").parse(date2);

		DateTime dt1 = new DateTime(DateSup);
		
		DateTime dt2 = new DateTime(DateInf);
					    			
		return Days.daysBetween(dt2, dt1).getDays() ;
		
	}
	
	// formatter date ( Date => String )  
	
	public String datetostring(Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    return  dateFormat.format(date);
		
	}
	
	// formatter date ( yyyy-MM-dd ) to ( dd-MM-yyyy )
	
	public String formatdate(String date) {
				
		return date.substring(8, 10) + date.substring(5, 7)+ date.substring(0, 4) ; 
		
	}
	
	
	
	

}
