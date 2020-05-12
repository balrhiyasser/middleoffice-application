package com.example.springsecuritypfe.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="awb_devise")
public class Devise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="libdevise")
    private String libdevise;

    @Column(name="unite")
    private Integer unite;

    @Column(name="active")
    private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibdevise() {
		return libdevise;
	}

	public void setLibdevise(String libdevise) {
		this.libdevise = libdevise;
	}

	public Integer getUnite() {
		return unite;
	}

	public void setUnite(Integer unite) {
		this.unite = unite;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
    
    


	
	

}
