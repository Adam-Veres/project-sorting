package com.epam.service;

import com.epam.model.EcoService;
import com.epam.repository.EcoServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EcoServiceService {

	EcoServiceRepository ecoServiceRepository;

	public List<EcoService> getAllEcoService(){
		return ecoServiceRepository.findAll();
	}
	
}
