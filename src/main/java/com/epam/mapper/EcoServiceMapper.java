package com.epam.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.model.EcoService;

@Mapper(componentModel = "spring", uses = CommonMapper.class)
public interface EcoServiceMapper {

	@Mapping(target = "rating", source = "id", qualifiedBy = RatingEncoderMapping.class)
	@Mapping(target = "comments", source = "comments", qualifiedBy = CommentsListMapping.class)
	EcoServiceDto ecoServiceToEcoServiceDto(EcoService ecoService);

	@Mapping(ignore = true, target = "owner")
	EcoService ecoServiceDtoToEcoService(EcoServiceDto ecoServiceDto);

	List<EcoServiceDto> ecoServiceListToEcoServiceListDto(List<EcoService> allEcoService);
	
	@Mapping(target = "rating", source = "id", qualifiedBy = RatingEncoderMapping.class)
	EcoServiceDtoNarrow ecoServiceToEcoServiceDtoNarrow(EcoService ecoService);

	List<EcoServiceDtoNarrow> ecoServiceListToEcoServiceListDtoNarrow(List<EcoService> serviceFromArea);
	
}
