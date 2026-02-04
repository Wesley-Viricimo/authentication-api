package org.authentication.controller;

import jakarta.validation.Valid;
import org.authentication.domain.dto.request.AuthRequestDTO;
import org.authentication.domain.dto.response.AuthResponseDTO;
import org.authentication.domain.dto.response.ResponseDataDTO;
import org.authentication.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ResponseDataDTO<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO authRequest) {
        ResponseDataDTO<AuthResponseDTO> response = authService.authenticate(authRequest);
        return ResponseEntity.ok(response);
    }
}