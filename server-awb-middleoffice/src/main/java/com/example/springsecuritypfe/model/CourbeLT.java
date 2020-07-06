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
@Table(name="awb_courbe_lt")
public class CourbeLT {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	 	@Column(name="dateCourbe")
	    private String dateCourbe;
	    
	    @Column(name="maturite")
	    private Integer maturite;

	    @Column(name="taux")
	    private Double taux;
	    
    
}
