package com.example.springsecuritypfe.service;

import java.util.List;

import com.example.springsecuritypfe.model.Parameter;

public interface ParameterService {
	
	Parameter saveParameter(Parameter parameter);

	Parameter updateParameter(Parameter parameter);

    void deleteParameter(Long parameterId);
    
    Parameter findByCle(String cle);

    Long numberOfParameters();

    List<Parameter> findAllParameters();

}
