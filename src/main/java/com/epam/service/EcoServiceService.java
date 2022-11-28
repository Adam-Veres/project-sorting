package com.epam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.model.EcoService;
import com.epam.repository.EcoServiceRepository;

@Service
public class EcoServiceService {
	
	@Autowired
	EcoServiceRepository ecoServiceRepository;

	public List<EcoService> getAllEcoService(){
		return ecoServiceRepository.findAll();
	}
	
}
