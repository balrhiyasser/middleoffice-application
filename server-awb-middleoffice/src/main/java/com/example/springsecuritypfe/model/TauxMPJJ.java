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
@Table(name="awb_tmp_jj")
public class TauxMPJJ  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="dateTaux")
    private String dateTaux;
    
    @Column(name="taux")
    private Double taux;
    
    @Column(name="maturite")
    private Long maturite = 1L ;

}
