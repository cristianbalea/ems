package org.sd.external.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sd.dtos.keycloak.GroupDto;
import org.sd.dtos.keycloak.KeycloakUserDto;
import org.sd.dtos.UserRegistrationRequest;
import org.sd.exceptions.AccountNotCreatedException;
import org.sd.exceptions.ExternalServiceException;
import org.sd.exceptions.ResourceNotRetrievedException;
import org.sd.external.keycloak.KeycloakRestClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalKeycloakService {
    private final KeycloakRestClient keycloakRestClient;

    public HttpStatus saveUser(UserRegistrationRequest userRegistrationRequest) {
        try {
            return keycloakRestClient.saveUser(userRegistrationRequest);
        } catch (ExternalServiceException ex) {
            String errorMessage = "Error creating user: " + ex.getMessage();
            log.error(errorMessage);
            throw new AccountNotCreatedException(errorMessage);
        }
    }

    public KeycloakUserDto getUserDetails(UUID userExternalId) {
        try {
            return keycloakRestClient.getUserDetailsDto(userExternalId);
        } catch (ExternalServiceException ex) {
            String errorMessage = "Error retrieving user details " + ex.getMessage();
            log.error(errorMessage);
            throw new ResourceNotRetrievedException(errorMessage);
        }
    }

    public List<KeycloakUserDto> getAllUserDetails() {
        try {
            return keycloakRestClient.getAllUserDetails();
        } catch (ExternalServiceException ex) {
            String errorMessage = "Error retrieving all user details " + ex.getMessage();
            log.error(errorMessage);
            throw new ResourceNotRetrievedException(errorMessage);
        }
    }

    public HttpStatus deleteUser(UUID externalId) {
        try {
            return keycloakRestClient.deleteUser(externalId);
        } catch (ExternalServiceException ex) {
            String errorMessage = "Error deleting user " + ex.getMessage();
            log.error(errorMessage);
            throw new ResourceNotRetrievedException(errorMessage);
        }
    }

    public List<GroupDto> getUserGroups(UUID externalId) {
        try {
            return keycloakRestClient.getUserRoles(externalId);
        } catch (ExternalServiceException ex) {
            String errorMessage = "Error retrieving group info: " + ex.getMessage();
            log.error(errorMessage);
            throw new ResourceNotRetrievedException(errorMessage);
        }
    }
}
