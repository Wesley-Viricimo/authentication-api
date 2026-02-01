package org.authentication.controller;

import jakarta.validation.Valid;
import org.authentication.domain.dto.request.UserCreateRequestDTO;
import org.authentication.domain.dto.response.ResponseDataDTO;
import org.authentication.domain.dto.response.UserResponseDTO;
import org.authentication.domain.enums.UserRole;
import org.authentication.service.UserRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-register")
public class UserRegisterController {
    private final UserRegisterService userRegisterService;

    public UserRegisterController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @PostMapping
    public ResponseEntity<ResponseDataDTO<UserResponseDTO>> create(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        ResponseDataDTO<UserResponseDTO> response = userRegisterService.createUser(userCreateRequestDTO, UserRole.USER);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<ResponseDataDTO<UserResponseDTO>> createAdmin(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        ResponseDataDTO<UserResponseDTO> response = userRegisterService.createUser(userCreateRequestDTO, UserRole.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
