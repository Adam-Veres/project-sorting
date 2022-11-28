package com.epam.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.epam.model.Coordinate;
import com.epam.model.DeliveryOption;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;

public class EcoServiceDto {

	long id;
	@NotBlank
	String serviceName;
	Set<WasteType> typeOfWastes;
	Set<PaymentCondition> paymentConditions;
	Set<DeliveryOption> deliveryOptions;
	@NotNull
	Coordinate coordinate;
	
	public EcoServiceDto() {
	}

	public EcoServiceDto(long id, String serviceName, Set<WasteType> typeOfWastes,
			Set<PaymentCondition> paymentConditions, Set<DeliveryOption> deliveryOptions, Coordinate coordinate) {
		this.id = id;
		this.serviceName = serviceName;
		this.typeOfWastes = typeOfWastes;
		this.paymentConditions = paymentConditions;
		this.deliveryOptions = deliveryOptions;
		this.coordinate = coordinate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Set<WasteType> getTypeOfWastes() {
		return typeOfWastes;
	}

	public void setTypeOfWastes(Set<WasteType> typeOfWastes) {
		this.typeOfWastes = typeOfWastes;
	}

	public Set<PaymentCondition> getPaymentConditions() {
		return paymentConditions;
	}

	public void setPaymentConditions(Set<PaymentCondition> paymentConditions) {
		this.paymentConditions = paymentConditions;
	}

	public Set<DeliveryOption> getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(Set<DeliveryOption> deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
}
