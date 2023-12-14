package org.sd.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sd.dtos.SimulatorMessage;
import org.sd.entities.MeasureEntity;

@Mapper
public interface MessageMapper {
    @Mapping(source = "device_id", target = "deviceId")
    @Mapping(source = "measurement_value", target = "measurementValue")
    MeasureEntity toEntity(SimulatorMessage simulatorMessage);
}
