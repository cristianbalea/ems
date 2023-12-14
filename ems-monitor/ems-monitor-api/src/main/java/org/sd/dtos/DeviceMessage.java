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
public class DeviceMessage {
    private UUID device_id;
    private UUID user_id;
    private Double maximumHourlyConsumption;
    private String device_name;
}
