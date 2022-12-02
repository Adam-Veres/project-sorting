package com.epam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
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
	@OneToOne(cascade = CascadeType.ALL)
	private Coordinate coordinate;
	private String description;
	@Setter(value = AccessLevel.NONE)
	private BigDecimal numOfRatings;
	@Setter(value = AccessLevel.NONE)
	private BigDecimal sumOfRatings;
	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	@Transient
	private BigDecimal rating;

	@JsonBackReference
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(nullable = false)
	private EcoUser owner;
	
	public EcoService(long id, String serviceName, Set<WasteType> typeOfWastes, Set<PaymentCondition> paymentConditions,
			Set<DeliveryOption> deliveryOptions, Coordinate coordinate, String description, BigDecimal numOfRatings, BigDecimal sumOfRatings, EcoUser owner) {
		this.id = id;
		this.serviceName = serviceName;
		this.typeOfWastes = typeOfWastes;
		this.paymentConditions = paymentConditions;
		this.deliveryOptions = deliveryOptions;
		this.coordinate = coordinate;
		this.description = description;
		this.numOfRatings = numOfRatings;
		this.sumOfRatings = sumOfRatings;
		this.owner = owner;
	}
	
	public void addRating(double rating) {
		BigDecimal rate = BigDecimal.valueOf(rating);
		if(this.sumOfRatings == null) {
			this.sumOfRatings = rate;
			this.numOfRatings = BigDecimal.ONE;
		} else {
			this.sumOfRatings = this.sumOfRatings.add(rate);
			this.numOfRatings = this.numOfRatings.add(BigDecimal.ONE);
		}
	}
	
	private void countRating() {
		if(this.sumOfRatings != null && this.numOfRatings != null && (this.numOfRatings.compareTo(BigDecimal.ZERO) != 0)) {
			this.rating =  this.sumOfRatings.divide(this.numOfRatings, new MathContext(2, RoundingMode.HALF_UP));
		} else {
			this.rating = BigDecimal.ZERO;
		}
	}

	public BigDecimal getRating() {
		countRating();
		return this.rating;
	}
	
}
