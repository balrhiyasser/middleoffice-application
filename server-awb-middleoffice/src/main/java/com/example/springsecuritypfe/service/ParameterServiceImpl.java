package com.example.springsecuritypfe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springsecuritypfe.model.Parameter;
import com.example.springsecuritypfe.repository.ParameterRepository;

@Service
public class ParameterServiceImpl implements ParameterService {
	
		@Autowired
	    private ParameterRepository parameterRepository;

	    @Override
	    public Parameter saveParameter(final Parameter parameter){
	    	parameterRepository.save(parameter);
	        return parameter;
	    }

	    @Override
	    public Parameter updateParameter(final Parameter parameter){
	        return parameterRepository.save(parameter);
	    }

	    @Override
	    public void deleteParameter(final Long parameterId){
	    	parameterRepository.deleteById(parameterId);
	    }

	    @Override
	    public Long numberOfParameters(){
	        return parameterRepository.count();
	    }

	    @Override
	    public List<Parameter> findAllParameters(){
	        return parameterRepository.findAll();
	    }

		@Override
		public Parameter findByCle(String cle) {
	        return parameterRepository.findByCle(cle);
		}


}
