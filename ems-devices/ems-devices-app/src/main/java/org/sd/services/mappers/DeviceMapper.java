package org.sd.services.mappers;

import org.mapstruct.Mapper;
import org.sd.dtos.DeviceDto;
import org.sd.entities.DeviceEntity;

import java.util.List;

@Mapper
public interface DeviceMapper {
    DeviceDto toDto(DeviceEntity device);
}
