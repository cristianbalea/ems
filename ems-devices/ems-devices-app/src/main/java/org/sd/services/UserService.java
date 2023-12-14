package org.sd.services;

import lombok.RequiredArgsConstructor;
import org.sd.dtos.ResponseMessage;
import org.sd.dtos.UpdateUserRequest;
import org.sd.entities.UserEntity;
import org.sd.exceptions.EntityNotFoundException;
import org.sd.repositories.AddressRepository;
import org.sd.repositories.DeviceRepository;
import org.sd.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public ResponseMessage updateUser(UpdateUserRequest updateUserRequest) {
        UserEntity userToBeUpdated = userRepository.findByUserExternalId(updateUserRequest.getUserExternalId())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        userToBeUpdated.setFirstname(updateUserRequest.getFirstname());
        userToBeUpdated.setLastname(updateUserRequest.getLastname());
        userRepository.save(userToBeUpdated);

        return ResponseMessage.builder().message("User updated successfully!").build();
    }

    @Transactional
    public ResponseMessage deleteUser(UUID userExternalId) {
        addressRepository.deleteByDevice_UserExternalId(userExternalId);
        deviceRepository.deleteAllByUserExternalId(userExternalId);
        userRepository.deleteByUserExternalId(userExternalId);

        return ResponseMessage.builder().message("User deleted successfully!").build();
    }
}
