package org.sd;

import jakarta.validation.Valid;
import org.sd.dtos.ResponseMessage;
import org.sd.dtos.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
public interface UserApi {
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/user/update", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest);

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/user/delete/{userExternalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseMessage> deleteUser(@PathVariable("userExternalId") UUID userExternalId);
}
