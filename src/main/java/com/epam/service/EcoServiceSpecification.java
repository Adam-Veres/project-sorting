package com.epam.service;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.epam.model.Coordinate_;
import com.epam.model.DeliveryOption;
import com.epam.model.EcoService;
import com.epam.model.EcoService_;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;

public class EcoServiceSpecification {

	public static Specification<EcoService> hasAreaLatitude(final BigDecimal startLatitude, final BigDecimal stopLatitude) {
		return (root, cq, cb) -> cb.between(root.get(EcoService_.coordinate).get(Coordinate_.latitude), startLatitude, stopLatitude);
	}
	
	public static Specification<EcoService> hasAreaLongitude(final BigDecimal startLongitude, final BigDecimal stopLongitude) {
		return (root, cq, cb) -> cb.between(root.get(EcoService_.coordinate).get(Coordinate_.longitude), startLongitude, stopLongitude);
	}
	
	public static Specification<EcoService> hasTypeOfWaste(final WasteType wasteType) {
		return (root, cq, cb) -> cb.isMember(wasteType, root.get(EcoService_.typeOfWastes));
	}
	
	public static Specification<EcoService> hasDeliveryOption(final DeliveryOption deliveryOption) {
		return (root, cq, cb) -> cb.isMember(deliveryOption, root.get(EcoService_.deliveryOptions));
	}
	
	public static Specification<EcoService> hasPaymentCondition(final PaymentCondition paymentCondition) {
		return (root, cq, cb) -> cb.isMember(paymentCondition, root.get(EcoService_.paymentConditions));
	}
	
}
