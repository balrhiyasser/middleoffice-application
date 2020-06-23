package com.example.springsecuritypfe.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
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

}
