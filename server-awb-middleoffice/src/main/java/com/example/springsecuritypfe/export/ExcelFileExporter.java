package com.example.springsecuritypfe.export;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.springsecuritypfe.model.CoursBBE;

public class ExcelFileExporter {
	
	public static ByteArrayInputStream coursListToExcelFile(List<CoursBBE> coursbbe) {
		
		try(Workbook workbook = new XSSFWorkbook()){
			
			Sheet sheet = workbook.createSheet("CoursList");
			
			Row row = sheet.createRow(0);
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        // Creating header
	        
	        Cell cell = row.createCell(0);
	        cell.setCellValue("achatClientele");
	        cell.setCellStyle(headerCellStyle);
	        
	        cell = row.createCell(1);
	        cell.setCellValue("venteClientele");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(2);
	        cell.setCellValue("achatClienteleCAL");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(3);
	        cell.setCellValue("venteClienteleCAL");
	        cell.setCellStyle(headerCellStyle);
	        
	        cell = row.createCell(4);
	        cell.setCellValue("midBAM");
	        cell.setCellStyle(headerCellStyle);
	        
	        cell = row.createCell(5);
	        cell.setCellValue("achatinterBAM");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(6);
	        cell.setCellValue("venteinterBAM");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(7);
	        cell.setCellValue("rachatinter");
	        cell.setCellStyle(headerCellStyle);
	        
	        cell = row.createCell(8);
	        cell.setCellValue("venteinter");
	        cell.setCellStyle(headerCellStyle);
	        
	        cell = row.createCell(9);
	        cell.setCellValue("rachatsousdel");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(10);
	        cell.setCellValue("libDevise");
	        cell.setCellStyle(headerCellStyle);
	
	        cell = row.createCell(11);
	        cell.setCellValue("uniteDevise");
	        cell.setCellStyle(headerCellStyle);
	        
	        cell = row.createCell(12);
	        cell.setCellValue("date");
	        cell.setCellStyle(headerCellStyle);
	        
	        // Creating data rows for each customer
	        
	        for(int i = 0; i < coursbbe.size(); i++) {
	        	
	        	Row dataRow = sheet.createRow(i + 1);
	        	dataRow.createCell(0).setCellValue(coursbbe.get(i).getAchatClientele());
	        	dataRow.createCell(1).setCellValue(coursbbe.get(i).getVenteClientele());
	        	dataRow.createCell(2).setCellValue(coursbbe.get(i).getAchatClienteleCAL());
	        	dataRow.createCell(3).setCellValue(coursbbe.get(i).getVenteClienteleCAL());
	        	dataRow.createCell(4).setCellValue(coursbbe.get(i).getMidBAM());
	        	dataRow.createCell(5).setCellValue(coursbbe.get(i).getAchatinterBAM());
	        	dataRow.createCell(6).setCellValue(coursbbe.get(i).getVenteinterBAM());
	        	dataRow.createCell(7).setCellValue(coursbbe.get(i).getRachatinter());
	        	dataRow.createCell(8).setCellValue(coursbbe.get(i).getVenteinter());
	        	dataRow.createCell(9).setCellValue(coursbbe.get(i).getRachatsousdel());
	        	dataRow.createCell(10).setCellValue(coursbbe.get(i).getLibDevise());
	        	dataRow.createCell(11).setCellValue(coursbbe.get(i).getUniteDevise());
	        	dataRow.createCell(12).setCellValue(coursbbe.get(i).getDate());
	        }
	
	        // Making size of column auto resize to fit with data
	        
	        for (int i = 0; i < 13; i++) {
		        sheet.autoSizeColumn(i);
			}

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        return new ByteArrayInputStream(outputStream.toByteArray());
	        
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
