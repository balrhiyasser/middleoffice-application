package com.example.springsecuritypfe.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


	@Entity
	@Data
	@Table(name="courbebdt")
	public class CourbeBDT  {
		
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name="dateEcheance")
	    private String dateEcheance;

	    @Column(name="dateValeur")
	    private String dateValeur;

	    @Column(name="dateCourbe")
	    private String dateCourbe;
	    
	    @Column(name="maturite")
	    private Long maturite;  

	    @Column(name="tmp")
	    private Double tmp;
	    
	    @Column(name="volume")
	    private Double volume;
	    
		
}

