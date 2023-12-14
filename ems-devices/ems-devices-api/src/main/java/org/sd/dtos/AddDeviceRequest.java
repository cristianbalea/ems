package org.sd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddDeviceRequest {
    @NotNull
    private UUID userExternalId;
    @NotNull
    private String userFirstname;
    @NotNull
    private String userLastname;
    @NotNull
    private String userEmail;
    @NotBlank
    private String description;
    @NotNull
    private Double maximumHourlyConsumption;
    @NotBlank
    private String country;
    @NotBlank
    private String county;
    @NotBlank
    private String city;
    @NotBlank
    private String number;
}
