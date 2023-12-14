package org.sd.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.sd.dtos.ResponseMessage;
import org.sd.dtos.UpdateUserRequest;
import org.sd.dtos.UserDto;
import org.sd.dtos.UserRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
public interface UserApi {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/user/register", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserDto>> getAllUsers();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/user/{userExternalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> getUser(@PathVariable @NotNull UUID userExternalId);

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/user/update", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest);

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/user/{cnp}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> deleteUser(@PathVariable @NotNull String cnp);
}
