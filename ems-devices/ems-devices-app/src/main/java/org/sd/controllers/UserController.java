package org.sd.controllers;

import lombok.RequiredArgsConstructor;
import org.sd.UserApi;
import org.sd.dtos.ResponseMessage;
import org.sd.dtos.UpdateUserRequest;
import org.sd.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public ResponseEntity<ResponseMessage> updateUser(UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteUser(UUID userExternalId) {
        return ResponseEntity.ok(userService.deleteUser(userExternalId));
    }
}
