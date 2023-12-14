package org.sd.dtos;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String cnp;
    private AddressDto addressDto;
    private String email;
    private UUID userExternalId;
    private RoleDto role;
}
