package org.sd.external.devices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sd.exceptions.ExternalServiceException;
import org.sd.exceptions.ResourceNotRetrievedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalDeviceService {
    private final DeviceRestClient deviceRestClient;
}
