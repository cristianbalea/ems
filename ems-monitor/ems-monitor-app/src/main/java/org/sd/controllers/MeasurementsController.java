package org.sd.controllers;

import lombok.RequiredArgsConstructor;
import org.sd.api.MeasurementsApi;
import org.sd.dtos.MeasureDto;
import org.sd.services.MonitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementsController implements MeasurementsApi {
    private final MonitorService monitorService;

    @Override
    public ResponseEntity<MeasureDto> getMeasurements(UUID deviceId, LocalDate date) {
        return ResponseEntity.ok(monitorService.getMeasures(deviceId, date));
    }
}
