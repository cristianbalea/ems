package org.sd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    @NotNull
    private UUID userExternalId;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String country;
    @NotBlank
    private String county;
    @NotBlank
    private String city;
    @NotBlank
    private String number;
}
