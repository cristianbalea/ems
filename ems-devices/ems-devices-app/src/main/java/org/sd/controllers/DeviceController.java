package org.sd.controllers;

import lombok.RequiredArgsConstructor;
import org.sd.DeviceApi;
import org.sd.dtos.AddDeviceRequest;
import org.sd.dtos.DeviceDto;
import org.sd.dtos.ResponseMessage;
import org.sd.dtos.UpdateUserRequest;
import org.sd.services.DeviceService;
import org.sd.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController implements DeviceApi {
    private final DeviceService deviceService;

    @Override
    public ResponseEntity<List<DeviceDto>> getUserDevices(UUID userExternalId) {
        return ResponseEntity.ok(deviceService.getUserDevices(userExternalId));
    }

    @Override
    public ResponseEntity<DeviceDto> addDevice(AddDeviceRequest addDeviceRequest) {
        return ResponseEntity.ok(deviceService.addDevice(addDeviceRequest));
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteDevice(UUID deviceExternalId) {
        return ResponseEntity.ok(deviceService.deleteDevice(deviceExternalId));
    }

    @Override
    public ResponseEntity<DeviceDto> updateDevice(AddDeviceRequest addDeviceRequest, UUID deviceExternalId) {
        return ResponseEntity.ok(deviceService.updateDevice(addDeviceRequest, deviceExternalId));
    }
}
