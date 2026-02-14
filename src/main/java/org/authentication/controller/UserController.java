package org.authentication.controller;

import org.authentication.domain.dto.response.MessageResponseDTO;
import org.authentication.domain.dto.response.ResponseDataDTO;
import org.authentication.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDataDTO<MessageResponseDTO>> logout() {
        ResponseDataDTO<MessageResponseDTO> response = this.authService.logout();
        return ResponseEntity.ok(response);
    }
}
