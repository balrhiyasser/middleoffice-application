package com.example.springsecuritypfe.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
	
	/*"achatClientele": 10.888,
    "date": "2020-03-25T08:30:00",
    "libDevise": "GIP",
    "uniteDevise": 1,
    "venteClientele": 12.654 */
	
	@Data
	@Entity
	@Table(name="coursbbe")
	public class CoursBBE  {
		
		/*
		•	Libelle devise
		•	Date
		•	Unité devise
		•	Achat clientèle BAM
		•	Vente clientèle BAM
		•	Mid BAM : (Achat clientèle BAM + Vente clientèle BAM)/2 (arrondi à 4 chiffres après la virgule)
		•	Achat aux intermédiaires BAM : Mid BAM *(1-commissionOfficeChange)
		•	Vente aux intermédiaires BAM : Mid BAM *(1+commisionOfficeChange)
		•	Achat Clientèle : Mid BAM *(1-commissionAWB)
		•	Vente Clientèle : Mid BAM *(1+commisionAWB)
		•	Vente aux intermédiaires : Mid BAM 
		•	Rachat aux intermédiaires : Achat aux intermédiaires BAM 
		•	Rachat aux Sous délégataires : Mid BAM *(1-commisionAWBDelegataire)
		*/

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name="achatClienteleBAM")
	    private Double achatClientele;

	    @Column(name="venteClienteleBAM")
	    private Double venteClientele;

	    @Column(name="achatClientelecal")
	    private Double achatClienteleCAL;

	    @Column(name="venteClientelecal")
	    private Double venteClienteleCAL;
	    
	    @Column(name="midbam")
	    private Double midBAM;
	    
	    @Column(name="achatinterbam")
	    private Double achatinterBAM;
	    
	    @Column(name="venteinterbam")
	    private Double venteinterBAM;
	    
	    @Column(name="rachatinter")
	    private Double rachatinter;
	    
	    @Column(name="venteinter")
	    private Double venteinter ;

	    @Column(name="rachatsousdel")
	    private Double rachatsousdel;
	    
	    @Column(name="libDevise")
	    private String libDevise;
	    
	    @Column(name="uniteDevise")
	    private Integer uniteDevise;

	    @Column(name="date")
	    private String date;
	    
	    
	    // Getters / Setters
	    
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		//=======================================================================


		public Double getAchatClientele() {
			return achatClientele;
		}

		public void setAchatClientele(Double achatClientele) {
			this.achatClientele = achatClientele;
		}
		
		//=======================================================================


		public Double getVenteClientele() {
			return venteClientele;
		}

		public void setVenteClientele(Double venteClientele) {
			this.venteClientele = venteClientele;
		}
		
		
		//=======================================================================
		
		public Double getAchatClienteleCAL() {
			return achatClienteleCAL;
		}

		public void setAchatClienteleCAL(Double midBAM, Parameter commissionAWB ) {
			this.achatClienteleCAL =  (double) Math.round((midBAM*(1-Double.parseDouble(commissionAWB.getValeur()))) * 10000) / 10000 ;
		}
		
		//================== (double) Math.round(monDouble * 10000) / 10000 =====================================================
		
		public Double getVenteClienteleCAL() {
			return venteClienteleCAL;
		}

		public void setVenteClienteleCAL(Double midBAM, Parameter commissionAWB) {
			this.venteClienteleCAL = (double) Math.round((midBAM*(1+Double.parseDouble(commissionAWB.getValeur()))) * 10000) / 10000 ;
		}
		
		//=======================================================================


		public Double getAchatinterBAM() {
			return achatinterBAM;
		}

		public void setAchatinterBAM(Double midBAM, Parameter commissionOfficeChange ) {
			this.achatinterBAM = (double) Math.round((midBAM*(1-Double.parseDouble(commissionOfficeChange.getValeur()))) * 10000) / 10000 ;
		}
		
		//=======================================================================


		public Double getVenteinterBAM() {
			return venteinterBAM;
		}

		public void setVenteinterBAM(Double midBAM, Parameter commissionOfficeChange ) {
			this.venteinterBAM = (double) Math.round((midBAM*(1+Double.parseDouble(commissionOfficeChange.getValeur()))) * 10000) / 10000 ;
		}
		
		//=======================================================================


		public Double getRachatinter() {
			return rachatinter;
		}
		
		public void setRachatinter(Double achatinterBAM) {
			this.rachatinter = achatinterBAM;
		}
		
		//=======================================================================


		public Double getVenteinter() {
			return venteinter;
		}
		
		public void setVenteinter(Double midBAM) {
			this.venteinter = midBAM;
		}
		
		//=======================================================================


		public Double getRachatsousdel() {
			return rachatsousdel;
		}
		
		public void setRachatsousdel(Double midBAM, Parameter commisionAWBDelegataire ) {
			this.rachatsousdel = (double) Math.round((midBAM*(1-Double.parseDouble(commisionAWBDelegataire.getValeur()))) * 10000) / 10000 ; 
		}
		
		//=======================================================================


		public Double getMidBAM() {
			return midBAM;
		}

		public void setMidBAM(Double achatClienteleBAM,Double venteClienteleBAM) {
			this.midBAM = (double) Math.round(((achatClienteleBAM + venteClienteleBAM)/2) * 10000) / 10000;
		}
		
		//=======================================================================

		public void setDate(String date) {
			this.date = date.substring(0,10);
		}
		
		public String getDate() {
			return date;
		}
		
		//=======================================================================


		public String getLibDevise() {
			return libDevise;
		}

		public void setLibDevise(String libDevise) {
			this.libDevise = libDevise;
		}
		
		//=======================================================================


		public Integer getUniteDevise() {
			return uniteDevise;
		}

		public void setUniteDevise(Integer uniteDevise) {
			this.uniteDevise = uniteDevise;
		}
		
		
		
}
