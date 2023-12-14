package org.sd;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.sd.dtos.AddDeviceRequest;
import org.sd.dtos.DeviceDto;
import org.sd.dtos.ResponseMessage;
import org.sd.dtos.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
public interface DeviceApi {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/user/{userExternalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DeviceDto>> getUserDevices(@PathVariable @NotNull UUID userExternalId);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DeviceDto> addDevice(@RequestBody @Valid AddDeviceRequest addDeviceRequest);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DeviceDto>> getAllDevices();

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/delete/{deviceExternalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> deleteDevice(@PathVariable @NotNull UUID deviceExternalId);

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/update/{deviceExternalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DeviceDto> updateDevice(
            @RequestBody @Valid AddDeviceRequest addDeviceRequest,
            @PathVariable @NotNull UUID deviceExternalId);
}
