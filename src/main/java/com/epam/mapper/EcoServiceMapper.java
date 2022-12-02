package com.epam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.model.EcoService;

@Mapper(componentModel = "spring")
public interface EcoServiceMapper {

	EcoServiceDto ecoServiceToEcoServiceDto(EcoService ecoService);
	
	@Mapping(ignore = true, target = "rating")
	EcoService ecoServiceDtoToEcoService(EcoServiceDto ecoServiceDto);

	List<EcoServiceDto> ecoServiceListToEcoServiceListDto(List<EcoService> allEcoService);
	
	EcoServiceDtoNarrow ecoServiceToEcoServiceDtoNarrow(EcoService ecoService);

	List<EcoServiceDtoNarrow> ecoServiceListToEcoServiceListDtoNarrow(List<EcoService> serviceFromArea);
	
//	CoordinateDto coordinateToCoordinateDto(Coordinate coordinate);
//	
//	Coordinate coordinateDtoToCoordinate(CoordinateDto coordinateDto);
	
}
