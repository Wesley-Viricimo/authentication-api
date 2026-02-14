package org.authentication.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(
        @NotBlank(message = "Refresh token não pode estar vazio")
        String refreshToken
) {}



