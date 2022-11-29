package com.epam.model;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EcoService {

	@Id
	@GeneratedValue
	private long id;
	private String serviceName;
	@ElementCollection(targetClass = WasteType.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<WasteType> typeOfWastes;
	@ElementCollection(targetClass = PaymentCondition.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<PaymentCondition> paymentConditions;
	@ElementCollection(targetClass = DeliveryOption.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<DeliveryOption> deliveryOptions;
	@OneToOne
	private Coordinate coordinate;
	
}
