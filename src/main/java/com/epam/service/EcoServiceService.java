package com.epam.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.epam.dto.CoordinateDto;
import com.epam.model.Coordinate;
import com.epam.model.DeliveryOption;
import com.epam.model.EcoService;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;
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
	 * @return List of Eco Services in range from database
	 */
	public List<EcoService> getServiceFromArea(final CoordinateDto coordinate, final BigDecimal distance) {
		if(coordinate == null || coordinate.getLatitude() == null || coordinate.getLongitude() == null || distance == null) {
			throw new IllegalArgumentException("Value can not be null!");
		}
		BigDecimal startLatitude = coordinateValidation(Optional.of(coordinate.getLatitude().subtract(distance.divide(ONE_DEGREE, MC))));
		BigDecimal stopLatitude = coordinateValidation(Optional.of(coordinate.getLatitude().add(distance.divide(ONE_DEGREE, MC))));
		BigDecimal startLongitude = coordinateValidation(Optional.of(coordinate.getLongitude().subtract(distance.divide(ONE_DEGREE, MC))));
		BigDecimal stopLongitude = coordinateValidation(Optional.of(coordinate.getLongitude().add(distance.divide(ONE_DEGREE, MC))));
		return ecoServiceRepository.findAllByCoordinateBetweenBorders(startLatitude, stopLatitude, startLongitude, stopLongitude);
	}
	
	/*
	 * Validate a coordinate. It is in range: -180 - 180. Recount the border slided coordinates.
	 * 
	 */
	private BigDecimal coordinateValidation(Optional<BigDecimal> valueRecieved) {
		BigDecimal value = valueRecieved.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
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

	public List<EcoService> getFilteredService(EcoService ecoService, BigDecimal distance) {
		Coordinate coordinate = ecoService.getCoordinate();
		BigDecimal startLatitude = coordinateValidation(coordinate.getLatitude().subtract(distance.divide(ONE_DEGREE, MC)));
		BigDecimal stopLatitude = coordinateValidation(coordinate.getLatitude().add(distance.divide(ONE_DEGREE, MC)));
		BigDecimal startLongitude = coordinateValidation(coordinate.getLongitude().subtract(distance.divide(ONE_DEGREE, MC)));
		BigDecimal stopLongitude = coordinateValidation(coordinate.getLongitude().add(distance.divide(ONE_DEGREE, MC)));
		Set<DeliveryOption> deliveryOptions = ecoService.getDeliveryOptions();
		Set<PaymentCondition> paymentConditions = ecoService.getPaymentConditions();
		Set<WasteType> typesOfWaste = ecoService.getTypeOfWastes();
		Specification<EcoService> spec = Specification.where(null);
		
		spec = spec.and(EcoServiceSpecification.hasAreaLatitude(startLatitude, stopLatitude));
		spec = spec.and(EcoServiceSpecification.hasAreaLongitude(startLongitude, stopLongitude));
		
		if(!ecoService.getDeliveryOptions().isEmpty()) {
			for (DeliveryOption deliveryOption : deliveryOptions) {
				spec = spec.and(EcoServiceSpecification.hasDeliveryOption(deliveryOption));
			}
		}
		
		if(!ecoService.getPaymentConditions().isEmpty()) {
			for (PaymentCondition paymentCondition : paymentConditions) {
				spec = spec.and(EcoServiceSpecification.hasPaymentCondition(paymentCondition));
			}
		}
		
		if(!ecoService.getTypeOfWastes().isEmpty()) {
			for (WasteType wasteType : typesOfWaste) {
				spec = spec.and(EcoServiceSpecification.hasTypeOfWaste(wasteType));
			}
		}
		
		return ecoServiceRepository.findAll(spec);
	}

	public EcoService getServiceById(Optional<Long> id) {
		Long identifier = id.orElseThrow(() -> new IllegalArgumentException("Given ID is not a valid format!"));
		return ecoServiceRepository.findById(identifier).get();
	}
}
