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

    public HttpStatus saveUser(UserRegistrationRequest userRegistrationRequest) {
        String accessToken = getAccessToken().getAccess_token();

        CredentialRepresentationRequest credentialRepresentationRequest = CredentialRepresentationRequest.builder()
                .type("password")
                .value(userRegistrationRequest.getPassword())
                .temporary(false)
                .build();

        UserRepresentationRequest userRepresentationRequest = UserRepresentationRequest.builder()
                .username(userRegistrationRequest.getCnp())
                .enabled(true)
                .email(userRegistrationRequest.getEmail())
                .credentials(Collections.singletonList(credentialRepresentationRequest))
                .groups(Collections.singletonList("user_roles"))
                .build();

        return keycloakWebClient
                .post()
                .uri("admin/realms/ems/users")
                .bodyValue(userRepresentationRequest)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }

    public KeycloakUserDto getUserDetailsDto(UUID userExternalId) {
        String accessToken = getAccessToken().getAccess_token();

        return keycloakWebClient
                .get()
                .uri("admin/realms/ems/users/" + userExternalId)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KeycloakUserDto.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }

    public List<KeycloakUserDto> getAllUserDetails() {
        String accessToken = getAccessToken().getAccess_token();

        return keycloakWebClient
                .get()
                .uri("admin/realms/ems/users")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<KeycloakUserDto>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }

    public HttpStatus deleteUser(UUID externalId) {
        String accessToken = getAccessToken().getAccess_token();

        return keycloakWebClient
                .delete()
                .uri("admin/realms/ems/users/" + externalId)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }

    public List<GroupDto> getUserRoles(UUID externalId) {
        String accessToken = getAccessToken().getAccess_token();

        return keycloakWebClient
                .get()
                .uri("admin/realms/ems/users/" + externalId + "/groups")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GroupDto>>() {})
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(e -> Mono.error(new ExternalServiceException(e.getMessage())))
                .block();
    }
}
