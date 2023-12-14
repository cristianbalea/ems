package org.sd.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.sd.dtos.MeasureDto;
import org.sd.dtos.SimulatorMessage;
import org.sd.entities.DeviceEntity;
import org.sd.entities.MeasureEntity;
import org.sd.exceptions.EntityNotFoundException;
import org.sd.repositories.DeviceRepository;
import org.sd.repositories.MeasureRepository;
import org.sd.services.mappers.MessageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorService {
    private final MessageMapper mapper = Mappers.getMapper(MessageMapper.class);
    private final MeasureRepository measureRepository;
    private final DeviceRepository deviceRepository;
    private final NotificationService notificationService;
    private final String EXCEED_MESSAGE = "The consumption for the last hour exceeded the maximum value for device %s!";

    public void handleMeasure(SimulatorMessage message) {
        DeviceEntity device = saveMeasurement(message);
        checkHourlyConsumption(device);
    }

    private DeviceEntity saveMeasurement(SimulatorMessage message) {
        MeasureEntity measure = mapper.toEntity(message);
        Double sumOfPreviousMeasures = measureRepository
                .findAllByDeviceId(message.getDevice_id()).stream()
                .map(MeasureEntity::getMeasurementValue)
                .reduce(0.0, Double::sum);
        measure.setMeasurementValue(measure.getMeasurementValue() - sumOfPreviousMeasures);
        measureRepository.save(measure);

        DeviceEntity device = deviceRepository.findByDeviceId(message.getDevice_id())
                .orElseThrow(() -> new EntityNotFoundException("Device not found!"));
        device.setMeasureNumber(device.getMeasureNumber() + 1);

        return deviceRepository.save(device);
    }

    private void checkHourlyConsumption(DeviceEntity device) {
        if (device.getMeasureNumber() % 6 == 0) {
            log.info("Checking the consumption from last hour...");
            Double hourlyConsumption = measureRepository
                    .findAll(PageRequest.of(0, 6, Sort.by("timestamp").descending()))
                    .stream()
                    .map(MeasureEntity::getMeasurementValue)
                    .reduce(0.0, Double::sum);

            if (hourlyConsumption > device.getMaximumHourlyConsumption()) {
                String message = String.format(EXCEED_MESSAGE, device.getDeviceName());
                log.warn(message);
                notificationService.sendPrivateNotification(device.getUserId(), message);
            }
        }
    }

    public MeasureDto getMeasures(UUID deviceId, LocalDate date) {
        Pageable pageable = PageRequest.of(0, 6, Sort.by("timestamp").ascending());

        List<Double> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();

        int counter = 0;

        Page<MeasureEntity> result;
        do {
            result = measureRepository.findAllByDeviceIdAndTimestampBetween(
                    deviceId,
                    LocalDateTime.of(date, LocalTime.MIN),
                    LocalDateTime.of(date, LocalTime.MAX),
                    pageable);

            yValues.add(result.stream()
                    .map(MeasureEntity::getMeasurementValue)
                    .reduce(0.0, Double::sum));
            counter++;

            pageable = result.nextPageable();
        } while (result.hasNext());

        for (int i = 0; i < counter; i++) {
            xValues.add((double) i);
        }

        return MeasureDto.builder().xValues(xValues).yValues(yValues).build();
    }
}
