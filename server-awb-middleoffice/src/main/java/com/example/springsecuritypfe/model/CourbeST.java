package com.example.springsecuritypfe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name="awb_courbe_st")
public class CourbeST {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="dateCourbe")
    private String dateCourbe;
    
    @Column(name="dateEcheance")
    private String dateEcheance;
    
    @Column(name="maturite")
    private Integer maturite;  

    @Column(name="taux")
    private Double taux;

	public CourbeST(String dateCourbe, Integer maturite, Double taux) {
		super();
		this.dateCourbe = dateCourbe;
		this.maturite = maturite;
		this.taux = taux;
	}
    
    


}                                                                                                           
