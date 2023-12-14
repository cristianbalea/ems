package org.sd.services;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.sd.dtos.AddDeviceRequest;
import org.sd.dtos.DeviceDto;
import org.sd.dtos.MessageTemplate;
import org.sd.dtos.ResponseMessage;
import org.sd.entities.AddressEntity;
import org.sd.entities.DeviceEntity;
import org.sd.entities.UserEntity;
import org.sd.exceptions.EntityNotFoundException;
import org.sd.repositories.AddressRepository;
import org.sd.repositories.DeviceRepository;
import org.sd.repositories.UserRepository;
import org.sd.services.mappers.AddressMapper;
import org.sd.services.mappers.DeviceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final MessageProducer messageProducer;
    private final DeviceMapper deviceMapper = Mappers.getMapper(DeviceMapper.class);
    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    private final String ADDRESS_NOT_FOUND_MESSAGE = "Address not found for device %s!";
    private final String DEVICE_NOT_FOUND_MESSAGE = "Device not found!";


    public List<DeviceDto> getUserDevices(UUID userExternalId) {
        return deviceRepository.findAllByUserExternalId(userExternalId).stream().map(
                d -> {
                    AddressEntity address = getDeviceAddress(d);

                    return toDto(d, address);
                }
        ).collect(Collectors.toList());
    }

    private AddressEntity getDeviceAddress(DeviceEntity d) {
        return addressRepository.findByDevice_Id(d.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(ADDRESS_NOT_FOUND_MESSAGE, d.getDeviceExternalId())));
    }

    private DeviceDto toDto(DeviceEntity device, AddressEntity address) {
        UserEntity user = userRepository.findByUserExternalId(device.getUserExternalId())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        DeviceDto deviceDto = deviceMapper.toDto(device);
        deviceDto.setAddress(addressMapper.toDto(address));
        deviceDto.setUserFirstname(user.getFirstname());
        deviceDto.setUserLastname(user.getLastname());

        return deviceDto;
    }

    @Transactional
    public DeviceDto addDevice(AddDeviceRequest addDeviceRequest) {
        DeviceEntity savedDevice = deviceRepository.save(DeviceEntity.builder()
                .description(addDeviceRequest.getDescription())
                .maximumHourlyConsumption(addDeviceRequest.getMaximumHourlyConsumption())
                .userExternalId(addDeviceRequest.getUserExternalId())
                .build());

        AddressEntity address = addressRepository.save(AddressEntity.builder()
                .country(addDeviceRequest.getCountry())
                .county(addDeviceRequest.getCounty())
                .city(addDeviceRequest.getCity())
                .number(addDeviceRequest.getNumber())
                .device(savedDevice)
                .build());

        if (userRepository.findByUserExternalId(addDeviceRequest.getUserExternalId()).isEmpty()) {
            userRepository.save(UserEntity.builder()
                    .userExternalId(addDeviceRequest.getUserExternalId())
                    .firstname(addDeviceRequest.getUserFirstname())
                    .lastname(addDeviceRequest.getUserLastname())
                    .email(addDeviceRequest.getUserEmail())
                    .build());
        }

        messageProducer.sendMessage(MessageTemplate.builder()
                .device_id(savedDevice.getDeviceExternalId())
                .device_name(savedDevice.getDescription())
                .user_id(savedDevice.getUserExternalId())
                .maximumHourlyConsumption(savedDevice.getMaximumHourlyConsumption())
                .build());

        return toDto(savedDevice, address);
    }

    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll().stream().map(
                d -> {
                    AddressEntity address = getDeviceAddress(d);

                    return toDto(d, address);
                }
        ).collect(Collectors.toList());
    }

    @Transactional
    public ResponseMessage deleteDevice(UUID deviceExternalId) {
        DeviceEntity deviceToBeDeleted = deviceRepository.findByDeviceExternalId(deviceExternalId)
                .orElseThrow(() -> new EntityNotFoundException(DEVICE_NOT_FOUND_MESSAGE));

        AddressEntity addressToBeDeleted = getDeviceAddress(deviceToBeDeleted.getId());

        addressRepository.delete(addressToBeDeleted);
        deviceRepository.delete(deviceToBeDeleted);

        return ResponseMessage.builder()
                .message("Device deleted successfully!")
                .build();
    }

    private AddressEntity getDeviceAddress(int id) {
        return addressRepository.findByDevice_Id(id)
                .orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND_MESSAGE));
    }

    @Transactional
    public DeviceDto updateDevice(AddDeviceRequest request, UUID deviceExternalId) {
        DeviceEntity device = deviceRepository.findByDeviceExternalId(deviceExternalId)
                .orElseThrow(() -> new EntityNotFoundException(DEVICE_NOT_FOUND_MESSAGE));

        device.setDescription(request.getDescription());
        device.setMaximumHourlyConsumption(request.getMaximumHourlyConsumption());
        if (!device.getUserExternalId().equals(request.getUserExternalId())) {
            device.setUserExternalId(request.getUserExternalId());
            if (userRepository.findByUserExternalId(request.getUserExternalId()).isEmpty()) {
                userRepository.save(UserEntity.builder()
                        .userExternalId(request.getUserExternalId())
                        .firstname(request.getUserFirstname())
                        .lastname(request.getUserLastname())
                        .email(request.getUserEmail())
                        .build());
            }
        }
        deviceRepository.save(device);

        AddressEntity address = getDeviceAddress(device.getId());
        address.setCountry(request.getCountry());
        address.setCounty(request.getCounty());
        address.setCity(request.getCity());
        address.setNumber(request.getNumber());
        addressRepository.save(address);

        messageProducer.sendMessage(MessageTemplate.builder()
                .device_id(device.getDeviceExternalId())
                .device_name(device.getDescription())
                .user_id(device.getUserExternalId())
                .maximumHourlyConsumption(device.getMaximumHourlyConsumption())
                .build());

        return toDto(device, address);
    }
}
