package org.sd.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String cnp;
    @NotBlank
    private String country;
    @NotBlank
    private String county;
    @NotBlank
    private String city;
    @NotBlank
    private String number;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
