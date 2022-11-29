package com.epam.dto;

import com.epam.model.Coordinate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EcoServiceDtoNarrow {

	long id;
	String serviceName;
	Coordinate coordinate;
	
}
