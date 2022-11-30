package com.epam.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.mapper.EcoServiceMapper;
import com.epam.model.EcoService;
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
	 * You should get bottom-left (bl) and top-right (tr) coordinate
	 * @param blLatitude
	 * @param blLongitude
	 * @param trLatitude
	 * @param trLongitude
	 * @return list of Eco Services in the area (DTO)
	 */
	@GetMapping(params = {"bl_latitude", "bl_longitude", "tr_latitude", "tr_longitude"})
	public List<EcoServiceDtoNarrow> getServicesFromArea(@RequestParam(name = "bl_latitude") Optional<BigDecimal> blLatitude,
			@RequestParam(name = "bl_longitude") Optional<BigDecimal> blLongitude,
			@RequestParam(name = "tr_latitude") Optional<BigDecimal> trLatitude,
			@RequestParam(name = "tr_longitude") Optional<BigDecimal> trLongitude) {
		List<EcoServiceDtoNarrow> ecoServices = ecoServiceMapper.ecoServiceListToEcoServiceListDtoNarrow(
				ecoServiceService.getServiceFromArea(blLatitude, blLongitude, trLatitude, trLongitude));
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
	public EcoServiceDto getServiceById(@PathVariable Long id) {
		EcoService es = ecoServiceService.getServiceById(Optional.of(id)).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "No EcoServices exist with this id: " + id + "  in Database!"));
		EcoServiceDto ecoService = ecoServiceMapper.ecoServiceToEcoServiceDto(es);
		return ecoService;
	}
	
	/**
	 * 
	 * @param ecoServiceDto will an example for searching
	 * @param distance
	 * @return give back Eco Services what satisfied the requirements
	 */
	@PostMapping(params = {"bl_latitude", "bl_longitude", "tr_latitude", "tr_longitude"})
	public List<EcoServiceDtoNarrow> getFilteredServicesFromArea(@RequestBody EcoServiceDto ecoServiceDto, 
			@RequestParam(name = "bl_latitude") Optional<BigDecimal> blLatitude,
			@RequestParam(name = "bl_longitude") Optional<BigDecimal> blLongitude,
			@RequestParam(name = "tr_latitude") Optional<BigDecimal> trLatitude,
			@RequestParam(name = "tr_longitude") Optional<BigDecimal> trLongitude){
		List<EcoServiceDtoNarrow> ecoServices = ecoServiceMapper.ecoServiceListToEcoServiceListDtoNarrow(ecoServiceService.getFilteredService(
				ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto), blLatitude, blLongitude, trLatitude, trLongitude));
		if(!ecoServices.isEmpty()) {
			return ecoServices;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices in Database!");
	}
	
	/**
	 * Delete Eco Service from DB
	 * @param id of Eco Service for deletion
	 */
	@DeleteMapping(path = "/manage/{id}")
	public void deleteEcoService(@PathVariable Long id) {
		if(ecoServiceService.deleteEcoService(Optional.of(id))) {
			throw new ResponseStatusException(HttpStatus.OK, "Eco Service deleted!");
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
	}
	
	/**
	 * Add new Eco Service to DB
	 * @param ecoServiceDto Eco Service what you want to add database
	 * @return the DTO representation of saved entity
	 */
	@PostMapping(path = "/manage")
	public EcoServiceDto createEcoService(@RequestBody @Valid EcoServiceDto ecoServiceDto) {
		EcoServiceDto ecoService = ecoServiceMapper.ecoServiceToEcoServiceDto(ecoServiceService.createNewEcoService(
				Optional.of(ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto))));
		if(ecoService != null) {
			return ecoService;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
	}
	
	/**
	 * Modify existing Eco Service
	 * @param ecoServiceDto new datas of service, add unmodified fields too
	 * @param id of modified Eco Service
	 * @return the DTO representation of modified entity
	 */
	@PutMapping(path = "/manage/{id}")
	public EcoServiceDto updateEcoService(@RequestBody @Valid EcoServiceDto ecoServiceDto, @PathVariable Long id) {
		EcoServiceDto ecoService = ecoServiceMapper.ecoServiceToEcoServiceDto(ecoServiceService.updateEcoService(
				Optional.of(ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto)), Optional.of(id)));
		if(ecoService != null) {
			return ecoService;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
	}
	
}
