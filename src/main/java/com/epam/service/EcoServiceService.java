package com.epam.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.CoordinateDto;
import com.epam.model.EcoService;
import com.epam.repository.EcoServiceRepository;

@Service
public class EcoServiceService {
	
	@Autowired
	EcoServiceRepository ecoServiceRepository;
	/*
	 * There are 360 degree around the earth. One degree is 111.32 km.
	 */
	private static BigDecimal ONE_DEGREE = BigDecimal.valueOf(111.320);
	/*
	 * For use 6 digits behind the . in BigDecimal double value
	 */
	private static MathContext MC = new MathContext(6);

	/**
	 * @return List of all Eco Services from database
	 */
	public List<EcoService> getAllEcoService(){
		return ecoServiceRepository.findAll();
	}

	/**
	 * Search Eco Services in an area from database. Needed the central coordinate and distance from it.
	 * @param coordinate is the central coordinate.
	 * @param distance services from central coordinate
	 * @return List of Eco Services in range fromdatabase
	 */
	public List<EcoService> getServiceFromArea(CoordinateDto coordinate, BigDecimal distance) {
		if(coordinate == null || coordinate.getLatitude() == null || coordinate.getLongitude() == null || distance == null) {
			throw new IllegalArgumentException("Value can not be null!");
		}
		BigDecimal startLatitude = coordinateValidation(coordinate.getLatitude().subtract(distance.divide(ONE_DEGREE, MC)));
		BigDecimal stopLatitude = coordinateValidation(coordinate.getLatitude().add(distance.divide(ONE_DEGREE, MC)));
		BigDecimal startLongitude = coordinateValidation(coordinate.getLongitude().subtract(distance.divide(ONE_DEGREE, MC)));
		BigDecimal stopLongitude = coordinateValidation(coordinate.getLongitude().add(distance.divide(ONE_DEGREE, MC)));
		return ecoServiceRepository.findAllByCoordinateBetweenBorders(startLatitude, stopLatitude, startLongitude, stopLongitude);
	}
	
	/*
	 * Validate a coordinate. It is in range: -180 - 180. Recount the border slided coordinates.
	 * 
	 */
	private BigDecimal coordinateValidation(BigDecimal value) {
		if(value == null) {
			throw new IllegalArgumentException("Value can not be null!");
		}
		BigDecimal reminder = BigDecimal.valueOf(0);
		if(value.compareTo(BigDecimal.valueOf(180)) > 0 || value.compareTo(BigDecimal.valueOf(-180)) < 0) {
			if(value.compareTo(BigDecimal.valueOf(0)) > 0) {
				reminder = value.subtract(BigDecimal.valueOf(180), MC).multiply(BigDecimal.valueOf(2), MC);
			} else {
				reminder = value.add(BigDecimal.valueOf(180), MC).multiply(BigDecimal.valueOf(2), MC);
			}
			value = value.multiply(BigDecimal.valueOf(-1), MC).add(reminder, MC);
		}
		return value;
	}
}
