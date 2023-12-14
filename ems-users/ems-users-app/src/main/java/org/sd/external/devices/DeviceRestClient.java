package org.sd.external.devices;

import lombok.RequiredArgsConstructor;
import org.sd.dtos.UpdateUserRequest;
import org.sd.exceptions.ExternalServiceException;
import org.sd.external.keycloak.KeycloakRestClient;
import org.springframework.http.HttpStatus;
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

    public String updateUser(UpdateUserRequest updateUserRequest) {
        String accessToken = keycloakRestClient.getAccessToken().getAccess_token();

        return deviceWebClient
                .patch()
                .uri("/users/user/update")
                .bodyValue(updateUserRequest)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }

    public String deleteUser(UUID userExternalId) {
        String accessToken = keycloakRestClient.getAccessToken().getAccess_token();

        return deviceWebClient
                .delete()
                .uri("/users/user/delete/" + userExternalId)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }
}
