package org.sd.dtos.keycloak;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRepresentationRequest {
    private String username;
    private boolean enabled;
    private String email;
    private List<CredentialRepresentationRequest> credentials;
    private List<String> groups;
}
