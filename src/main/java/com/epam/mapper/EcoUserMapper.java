package com.epam.mapper;

import com.epam.dto.EcoUserDTO;
import com.epam.model.EcoUser;
import com.epam.security.EncodedMapping;
import com.epam.security.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface EcoUserMapper {

    EcoUserMapper INSTANCE = Mappers.getMapper( EcoUserMapper.class );

    @Mapping(target = "password", constant = "***")
    EcoUserDTO ecoUserToEcoUserDto(EcoUser ecoUser);

    @Mapping(source = "password", target = "withPassword", qualifiedBy = EncodedMapping.class)
    EcoUser ecoUserDtoToEcoUser(EcoUserDTO userDto);
}
