package com.epam.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.epam.model.Coordinate;
import com.epam.model.DeliveryOption;
import com.epam.model.PaymentCondition;
import com.epam.model.WasteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EcoServiceDto {

	long id;
	@NotBlank
	String serviceName;
	Set<WasteType> typeOfWastes;
	Set<PaymentCondition> paymentConditions;
	Set<DeliveryOption> deliveryOptions;
	@NotNull
	Coordinate coordinate;
	
}
