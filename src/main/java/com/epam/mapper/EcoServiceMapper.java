package com.epam.mapper;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.model.EcoService;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EcoServiceMapper {

  EcoServiceDto ecoServiceToEcoServiceDto(final EcoService ecoService);

  @Mapping(ignore = true, target = "rating")
  EcoService ecoServiceDtoToEcoService(final EcoServiceDto ecoServiceDto);

  List<EcoServiceDto> ecoServiceListToEcoServiceListDto(final List<EcoService> allEcoService);

  EcoServiceDtoNarrow ecoServiceToEcoServiceDtoNarrow(final EcoService ecoService);

  List<EcoServiceDtoNarrow> ecoServiceListToEcoServiceListDtoNarrow(
      final List<EcoService> serviceFromArea);
}
