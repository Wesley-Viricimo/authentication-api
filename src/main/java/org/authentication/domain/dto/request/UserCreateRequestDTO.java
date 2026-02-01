package org.authentication.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequestDTO(
        @NotBlank(message = "O campo 'name' não pode ser vazio")
        String name,

        @Email
        @NotBlank(message = "O campo 'email' não pode ser vazio")
        String email,

        @NotBlank(message = "O campo 'password' não pode ser vazio")
        String password
) {}
