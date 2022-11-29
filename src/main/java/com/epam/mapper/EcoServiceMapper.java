package com.epam.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.model.EcoService;

@Mapper(componentModel = "spring")
public interface EcoServiceMapper {

	
	EcoServiceDto ecoServiceToEcoServiceDto(EcoService ecoService);
	
	EcoService ecoServiceDtoToEcoService(EcoServiceDto ecoServiceDto);

	List<EcoServiceDto> ecoServiceListToEcoServiceListDto(List<EcoService> allEcoService);
	
	EcoServiceDtoNarrow ecoServiceToEcoServiceDtoNarrow(EcoService ecoService);

	List<EcoServiceDtoNarrow> ecoServiceListToEcoServiceListDtoNarrow(List<EcoService> serviceFromArea);
	
}
