package org.sd.external.devices;

import lombok.RequiredArgsConstructor;
import org.sd.exceptions.ExternalServiceException;
import org.sd.external.keycloak.KeycloakRestClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceRestClient {
    private final WebClient deviceWebClient;
    private final KeycloakRestClient keycloakRestClient;
}
