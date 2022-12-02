package com.epam.mapper;

import org.mapstruct.Mapper;

import com.epam.dto.CoordinateDto;
import com.epam.model.Coordinate;

@Mapper(componentModel = "spring")
public interface CoordinateMapper {

	CoordinateDto coordinateToCoordinateDto(Coordinate coordinate);
	
	Coordinate coordinateDtoToCoordinate(CoordinateDto coordinateDto);
	
}
