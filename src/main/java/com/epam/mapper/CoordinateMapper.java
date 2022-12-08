package com.epam.mapper;

import com.epam.dto.CoordinateDto;
import com.epam.model.Coordinate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinateMapper {

  CoordinateDto coordinateToCoordinateDto(final Coordinate coordinate);

  Coordinate coordinateDtoToCoordinate(final CoordinateDto coordinateDto);
}
