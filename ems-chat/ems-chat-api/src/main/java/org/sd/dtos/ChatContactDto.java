package org.sd.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatContactDto {
    private String firstname;
    private String lastname;
    private String email;
    private UUID userExternalId;
}
