package com.example.springsecuritypfe.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
	
	/*"dateEcheance": "2050-02-14",
	  "dateValeur": "2020-02-25",
	  "dateCourbe": "2020-02-25",
	  "tmp": 3.794,
	  "volume": 116.58 */
	
	@Data
	@Entity
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

	    @Column(name="tmp")
	    private Double tmp;
	    
	    @Column(name="volume")
	    private Double volume;
	    
	    
	    // Getters / Setters
	    
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDateEcheance() {
			return dateEcheance;
		}

		public void setDateEcheance(String dateEcheance) {
			this.dateEcheance = dateEcheance;
		}

		public String getDateValeur() {
			return dateValeur;
		}

		public void setDateValeur(String dateValeur) {
			this.dateValeur = dateValeur;
		}

		public String getDateCourbe() {
			return dateCourbe;
		}

		public void setDateCourbe(String dateCourbe) {
			this.dateCourbe = dateCourbe;
		}

		public Double getTmp() {
			return tmp;
		}

		public void setTmp(Double tmp) {
			this.tmp = tmp;
		}

		public Double getVolume() {
			return volume;
		}

		public void setVolume(Double volume) {
			this.volume = volume;
		}
		
		
		
}

