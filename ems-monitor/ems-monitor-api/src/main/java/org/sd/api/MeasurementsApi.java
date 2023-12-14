package org.sd.api;

import jakarta.validation.constraints.NotNull;
import org.sd.dtos.MeasureDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.UUID;

@Validated
public interface MeasurementsApi {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{deviceId}")
    ResponseEntity<MeasureDto> getMeasurements(@PathVariable @NotNull UUID deviceId, @RequestParam LocalDate date);
}
