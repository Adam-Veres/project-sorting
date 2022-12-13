package com.epam.mapper;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.model.EcoService;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CommonMapper.class)
public interface EcoServiceMapper {

  @Mapping(target = "rating", source = "id", qualifiedBy = RatingEncoderMapping.class)
  @Mapping(target = "comments", source = "comments", qualifiedBy = CommentsListMapping.class)
  EcoServiceDto ecoServiceToEcoServiceDto(final EcoService ecoService);

  @Mapping(ignore = true, target = "owner")
  EcoService ecoServiceDtoToEcoService(final EcoServiceDto ecoServiceDto);

  List<EcoServiceDto> ecoServiceListToEcoServiceListDto(final List<EcoService> allEcoService);

  @Mapping(target = "rating", source = "id", qualifiedBy = RatingEncoderMapping.class)
  EcoServiceDtoNarrow ecoServiceToEcoServiceDtoNarrow(final EcoService ecoService);

  List<EcoServiceDtoNarrow> ecoServiceListToEcoServiceListDtoNarrow(
      final List<EcoService> serviceFromArea);
}
