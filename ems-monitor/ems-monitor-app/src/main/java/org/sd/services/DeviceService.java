package org.sd.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sd.dtos.DeviceMessage;
import org.sd.entities.DeviceEntity;
import org.sd.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public void handleEvent(DeviceMessage message) {
        DeviceEntity device = deviceRepository.findByDeviceId(message.getDevice_id())
                .orElse(DeviceEntity.builder()
                        .deviceId(message.getDevice_id())
                        .measureNumber(0)
                        .build());
        device.setUserId(message.getUser_id());
        device.setMaximumHourlyConsumption(message.getMaximumHourlyConsumption());
        device.setDeviceName(message.getDevice_name());

        deviceRepository.save(device);
    }
}
