package org.sd;

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
public class MessageTemplate {
    private LocalDateTime timestamp;
    private UUID device_id;
    private Double measurement_value;
}
