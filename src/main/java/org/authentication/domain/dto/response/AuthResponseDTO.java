package org.authentication.domain.dto.response;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {}