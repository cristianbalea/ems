package org.sd.dtos.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialRepresentationRequest {
    private String type;
    private String value;
    private boolean temporary;
}
