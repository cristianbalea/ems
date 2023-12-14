package org.sd.services.mappers;

import org.mapstruct.Mapper;
import org.sd.dtos.AddressDto;
import org.sd.entities.AddressEntity;

@Mapper
public interface AddressMapper {
    AddressDto toDto(AddressEntity address);
}
