package org.authentication.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank(message = "O campo 'email' não pode ser vazio")
        String email,

        @NotBlank(message = "O campo 'password' não pode ser vazio")
        String password
) {}
