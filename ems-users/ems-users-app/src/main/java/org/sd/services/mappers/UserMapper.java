package org.sd.services.mappers;

import org.mapstruct.Mapper;
import org.sd.dtos.UserDto;
import org.sd.entities.UserEntity;

import java.util.List;

@Mapper(uses = AddressMapper.class)
public interface UserMapper {
    UserDto toDto(UserEntity user);
}
