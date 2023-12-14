package org.sd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DEVICE")
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "maximum_hourly_consumption")
    private Double maximumHourlyConsumption;

    @Column(name = "device_external_id")
    private UUID deviceExternalId;

    @Column(name = "user_external_id")
    private UUID userExternalId;

    @PrePersist
    void prePersist() {
        if (deviceExternalId == null) {
            deviceExternalId = UUID.randomUUID();
        }
    }
}
