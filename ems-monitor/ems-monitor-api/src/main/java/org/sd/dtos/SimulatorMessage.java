package org.sd.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulatorMessage {
    @NotNull
    private LocalDateTime timestamp;
    @NotNull
    private UUID device_id;
    @NotNull
    private Double measurement_value;
}
