package com.epam.mapper;

import com.epam.dto.EcoUserDTO;
import com.epam.model.EcoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = CommonMapper.class)
public interface EcoUserMapper {

  EcoUserMapper INSTANCE = Mappers.getMapper(EcoUserMapper.class);

  @Mapping(target = "password", constant = "***")
  EcoUserDTO ecoUserToEcoUserDto(final EcoUser ecoUser);

  @Mapping(target = "password", qualifiedBy = PasswordEncoderMapping.class)
  EcoUser ecoUserDtoToEcoUser(final EcoUserDTO userDto);
}
