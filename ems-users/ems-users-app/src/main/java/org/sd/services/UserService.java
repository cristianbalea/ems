package org.sd.services;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.sd.dtos.*;
import org.sd.dtos.keycloak.GroupDto;
import org.sd.dtos.keycloak.KeycloakUserDto;
import org.sd.entities.AddressEntity;
import org.sd.entities.UserEntity;
import org.sd.exceptions.AccountNotCreatedException;
import org.sd.exceptions.EntityNotFoundException;
import org.sd.external.devices.ExternalDeviceService;
import org.sd.external.keycloak.ExternalKeycloakService;
import org.sd.repositories.AddressRepository;
import org.sd.repositories.UserRepository;
import org.sd.services.mappers.AddressMapper;
import org.sd.services.mappers.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ExternalKeycloakService externalKeycloakService;
    private final ExternalDeviceService externalDeviceService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private final String USER_NOT_FOUND_MESSAGE = "User with cnp = %s not found!";
    private final String ADDRESS_NOT_FOUND_MESSAGE = "Address not found!";

    @Transactional
    public UserDto createUser(UserRegistrationRequest userRegistrationRequest) {
        validateCnpAndEmail(userRegistrationRequest);

        KeycloakUserDto keycloakUser = saveUserInKeycloak(userRegistrationRequest);

        UserEntity savedUser = saveUser(userRegistrationRequest, keycloakUser);

        AddressEntity savedAddress = getSavedAddress(userRegistrationRequest, savedUser);

        return toDto(savedUser, savedAddress, keycloakUser);
    }

    private void validateCnpAndEmail(UserRegistrationRequest userRegistrationRequest) {
        Optional<UserEntity> user = userRepository.findByCnp(userRegistrationRequest.getCnp());

        if (user.isPresent()) {
            throw new AccountNotCreatedException("User with this cnp already exists!");
        }

        if (externalKeycloakService.getAllUserDetails().stream()
                .anyMatch(existentUser -> existentUser.getEmail().equals(userRegistrationRequest.getEmail()))) {
            throw new AccountNotCreatedException("User with this email already exists!");
        }
    }

    private KeycloakUserDto saveUserInKeycloak(UserRegistrationRequest userRegistrationRequest) {
        externalKeycloakService.saveUser(userRegistrationRequest);

        return externalKeycloakService.getAllUserDetails().stream()
                .filter(u -> u.getEmail().equals(userRegistrationRequest.getEmail())).findFirst().
                orElseThrow(() -> new AccountNotCreatedException("There was a problem in creating the account!"));
    }

    private UserDto toDto(UserEntity user, AddressEntity address, KeycloakUserDto keycloakUser) {
        UserDto userDto = userMapper.toDto(user);
        userDto.setAddressDto(addressMapper.toDto(address));
        userDto.setEmail(keycloakUser.getEmail());
        userDto.setRole(getRole(user.getUserExternalId()));

        return userDto;
    }

    private RoleDto getRole(UUID userExternalId) {
        return getSuperiorRole(externalKeycloakService.getUserGroups(userExternalId));
    }

    private RoleDto getSuperiorRole(List<GroupDto> userGroups) {
        return userGroups.stream().map(GroupDto::getName).anyMatch(g -> g.equals("admin_roles")) ?
                RoleDto.ADMIN : RoleDto.USER;
    }

    private AddressEntity getSavedAddress(UserRegistrationRequest userRegistrationRequest, UserEntity savedUser) {
        return addressRepository.save(AddressEntity.builder()
                .user(savedUser)
                .country(userRegistrationRequest.getCountry())
                .county(userRegistrationRequest.getCounty())
                .city(userRegistrationRequest.getCity())
                .number(userRegistrationRequest.getNumber())
                .build());
    }

    private UserEntity saveUser(UserRegistrationRequest userRegistrationRequest, KeycloakUserDto keycloakUserDto) {
        return userRepository.save(UserEntity.builder()
                .firstname(userRegistrationRequest.getFirstname())
                .lastname(userRegistrationRequest.getLastname())
                .cnp(userRegistrationRequest.getCnp())
                .phoneNumber(userRegistrationRequest.getPhoneNumber())
                .userExternalId(keycloakUserDto.getId())
                .build());
    }

    public UserDto getUser(UUID userExternalId) {
        UserEntity user = userRepository.findUserEntityByUserExternalId(userExternalId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userExternalId)));

        AddressEntity address = getUserAddress(user.getId());

        KeycloakUserDto keycloakUserDto = externalKeycloakService.getUserDetails(userExternalId);

        return toDto(user, address, keycloakUserDto);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(
                u -> {
                    AddressEntity address = getUserAddress(u.getId());
                    KeycloakUserDto keycloakUserDto = externalKeycloakService.getUserDetails(u.getUserExternalId());

                    return toDto(u, address, keycloakUserDto);
                }
        ).collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUser(UpdateUserRequest updateUserRequest) {
        UserEntity user = updateUserDetails(updateUserRequest);

        AddressEntity address = updateAddressDetails(updateUserRequest, user);
        externalDeviceService.updateUser(updateUserRequest);

        return saveUpdatedUser(user, address);
    }

    private UserDto saveUpdatedUser(UserEntity user, AddressEntity address) {
        userRepository.save(user);
        addressRepository.save(address);

        KeycloakUserDto keycloakUserDto = externalKeycloakService.getUserDetails(user.getUserExternalId());
        return toDto(user, address, keycloakUserDto);
    }

    private AddressEntity updateAddressDetails(UpdateUserRequest updateUserRequest, UserEntity user) {
        AddressEntity address = getUserAddress(user.getId());

        address.setCountry(updateUserRequest.getCountry());
        address.setCounty(updateUserRequest.getCounty());
        address.setCity(updateUserRequest.getCity());
        address.setNumber(updateUserRequest.getNumber());
        return address;
    }

    private UserEntity updateUserDetails(UpdateUserRequest updateUserRequest) {
        UserEntity user = userRepository.findUserEntityByUserExternalId(updateUserRequest.getUserExternalId())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, updateUserRequest.getUserExternalId()))
                );

        user.setFirstname(updateUserRequest.getFirstname());
        user.setLastname(updateUserRequest.getLastname());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        return user;
    }

    @Transactional
    public ResponseMessage deleteUser(String cnp) {
        UserEntity userToBeDeleted = userRepository.findByCnp(cnp)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, cnp)));

        AddressEntity addressToBeDeleted = getUserAddress(userToBeDeleted.getId());

        addressRepository.delete(addressToBeDeleted);
        userRepository.deleteById(userToBeDeleted.getId());
        externalDeviceService.deleteUser(userToBeDeleted.getUserExternalId());
        externalKeycloakService.deleteUser(userToBeDeleted.getUserExternalId());

        return ResponseMessage.builder()
                .response("User deleted!")
                .build();
    }

    private AddressEntity getUserAddress(int id) {
        return addressRepository.findByUser_Id(id)
                .orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND_MESSAGE));
    }
}
