package com.example.springsecuritypfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecuritypfe.model.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

	//findBy + fieldName
    Parameter findByCle(String cle);
}
