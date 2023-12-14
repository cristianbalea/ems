package org.sd.external.keycloak;

import lombok.RequiredArgsConstructor;
import org.sd.dtos.*;
import org.sd.dtos.keycloak.*;
import org.sd.exceptions.ExternalServiceException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakRestClient {
    private final WebClient keycloakWebClient;

    public AccessToken getAccessToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "ems-users");
        body.add("grant_type", "client_credentials");
        body.add("client_secret", "O56oBnGqTFP9BuXUa5SnMNeol4wuYFHI");
        return keycloakWebClient
                .post()
                .uri("/realms/ems/protocol/openid-connect/token")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(AccessToken.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }
}
