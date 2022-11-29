package com.epam.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateDto {

	long id;
	@Min(value = -180)
	@Max(value = 180)
	BigDecimal latitude;
	@Min(value = -180)
	@Max(value = 180)
	BigDecimal longitude;
	
}
