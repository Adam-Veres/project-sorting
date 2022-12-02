package com.epam.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.epam.model.DeliveryOption;
import com.epam.model.EcoService;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;
import com.epam.repository.EcoServiceRepository;

@Service
public class EcoServiceService {
	
	@Autowired
	EcoServiceRepository ecoServiceRepository;
	
	@Autowired
	CoordinateService coordinateService;
	
	public List<EcoService> getAllEcoService(){
		return ecoServiceRepository.findAll();
	}


	/**
	 * You should get bottom-left (bl) and top-right (tr) coordinate
	 * @param blLatitude
	 * @param blLongitude
	 * @param trLatitude
	 * @param trLongitude
	 * @return List<EcoService>
	 */
	public List<EcoService> getServiceFromArea(Optional<BigDecimal> blLatitude, Optional<BigDecimal> blLongitude,
			Optional<BigDecimal> trLatitude, Optional<BigDecimal> trLongitude) {
		BigDecimal startLatitude = blLatitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
		BigDecimal startLongitude = blLongitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
		BigDecimal stopLatitude = trLatitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
		BigDecimal stopLongitude = trLongitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
		
		return ecoServiceRepository.findAllByCoordinateBetweenBorders(startLatitude, stopLatitude, startLongitude, stopLongitude);
	}

	/**
	 * Provide list of services in an area. You can specify different filters by parameterizing the example EcoService object and get bottom-left (bl) and top-right (tr) coordinate.
	 * @param ecoService (as an Example)
	 * @param blLatitude
	 * @param blLongitude
	 * @param trLatitude
	 * @param trLongitude
	 * @return List<EcoService>
	 */
	public List<EcoService> getFilteredService(EcoService ecoService, Optional<BigDecimal> blLatitude, Optional<BigDecimal> blLongitude,
			Optional<BigDecimal> trLatitude, Optional<BigDecimal> trLongitude) {
		BigDecimal startLatitude = blLatitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
		BigDecimal startLongitude = blLongitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
		BigDecimal stopLatitude = trLatitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
		BigDecimal stopLongitude = trLongitude.orElseThrow(() -> new IllegalArgumentException("Value can not be null!"));
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

	/**
	 * Providing an Eco Service by id.
	 * @param id of a service
	 * @return service what belongs to the given id
	 */
	public Optional<EcoService> getServiceById(Optional<Long> id) {
		Long identifier = id.orElseThrow(() -> new IllegalArgumentException("Given ID is not a valid format!"));
		EcoService es = ecoServiceRepository.findById(identifier).get();
		System.out.println(es.getRating());
		return Optional.of(es);
	}

}
