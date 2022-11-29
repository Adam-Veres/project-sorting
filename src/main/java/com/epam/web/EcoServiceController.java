package com.epam.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.epam.dto.CoordinateDto;
import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.mapper.EcoServiceMapper;
import com.epam.service.EcoServiceService;

@RestController
@RequestMapping("/api/ecoservice")
public class EcoServiceController {

	@Autowired
	EcoServiceService ecoServiceService;
	
	@Autowired
	EcoServiceMapper ecoServiceMapper;
	
	/**
	 * @return give back all Eco Services from database
	 */
	@GetMapping
	public List<EcoServiceDto> getAllServices() {
		List<EcoServiceDto> ecoServices = ecoServiceMapper.ecoServiceListToEcoServiceListDto(ecoServiceService.getAllEcoService());
		if(!ecoServices.isEmpty()) {
			return ecoServices;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices in Database!");
	}
	
	/**
	 * @param latitude of central coordinate
	 * @param longitude of central coordinate
	 * @param distance from central coordinate
	 * @return give back Eco Services from a range
	 */
	@GetMapping(path = "/{latitude}/{longitude}/{distance}")
	public List<EcoServiceDtoNarrow> getServicesFromArea(@PathVariable BigDecimal latitude, @PathVariable BigDecimal longitude, @PathVariable BigDecimal distance) {
		List<EcoServiceDtoNarrow> ecoServices = ecoServiceMapper.ecoServiceListToEcoServiceListDtoNarrow(ecoServiceService.getServiceFromArea(new CoordinateDto(0, latitude, longitude), distance));
		if(!ecoServices.isEmpty()) {
			return ecoServices;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices in Database!");
	}
	
	/**
	 * 
	 * @param id of service
	 * @return give back one Eco Service
	 */
	@GetMapping(path = "/{id}")
	public EcoServiceDtoNarrow getServiceById(@PathVariable Long id) {
		EcoServiceDtoNarrow ecoService = ecoServiceMapper.ecoServiceToEcoServiceDtoNarrow(ecoServiceService.getServiceById(Optional.of(id)));
		if(ecoService != null) {
			return ecoService;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices exist with this id: " + id + "  in Database!");
	}
	
	/**
	 * 
	 * @param ecoServiceDto will an example for searching
	 * @param distance
	 * @return give back Eco Services what satisfied the requirements
	 */
	@PostMapping(path = "/{distance}")
	public List<EcoServiceDtoNarrow> getFilteredServicesFromArea(@RequestBody EcoServiceDto ecoServiceDto, @PathVariable BigDecimal distance){
		List<EcoServiceDtoNarrow> ecoServices = ecoServiceMapper.ecoServiceListToEcoServiceListDtoNarrow(ecoServiceService.getFilteredService(ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto), distance));
		if(!ecoServices.isEmpty()) {
			return ecoServices;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices in Database!");
	}
	
}
