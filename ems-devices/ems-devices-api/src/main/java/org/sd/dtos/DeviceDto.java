package org.sd.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {
    private UUID deviceExternalId;
    private String description;
    private Double maximumHourlyConsumption;
    private AddressDto address;
    private UUID userExternalId;
    private String userFirstname;
    private String userLastname;
}
