package org.sd.external.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sd.dtos.keycloak.GroupDto;
import org.sd.dtos.keycloak.KeycloakUserDto;
import org.sd.exceptions.ExternalServiceException;
import org.sd.exceptions.ResourceNotRetrievedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalKeycloakService {
    private final KeycloakRestClient keycloakRestClient;
}
