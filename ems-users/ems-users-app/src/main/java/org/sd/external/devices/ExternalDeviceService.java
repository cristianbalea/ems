package org.sd.external.devices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sd.dtos.UpdateUserRequest;
import org.sd.exceptions.ExternalServiceException;
import org.sd.exceptions.ResourceNotRetrievedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalDeviceService {
    private final DeviceRestClient deviceRestClient;

    public String updateUser(UpdateUserRequest updateUserRequest) {
        try {
            return deviceRestClient.updateUser(updateUserRequest);
        } catch (ExternalServiceException ex) {
            String errorMessage = "Error updating user: " + ex.getMessage();
            log.error(errorMessage);
            throw new ResourceNotRetrievedException(errorMessage);
        }
    }

    public String deleteUser(UUID userExternalId) {
        try {
            return deviceRestClient.deleteUser(userExternalId);
        } catch (ExternalServiceException ex) {
            String errorMessage = "Error deleting user: " + ex.getMessage();
            log.error(errorMessage);
            throw new ResourceNotRetrievedException(errorMessage);
        }
    }
}
