package org.sd.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DEVICE")
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "device_id", unique = true)
    private UUID deviceId;
    @Column(name = "name")
    private String deviceName;
    @Column(name = "measure_number")
    private int measureNumber;
    @Column(name = "maximum_hourly_consumption")
    private Double maximumHourlyConsumption;
    @Column(name = "user_id")
    private UUID userId;
}
