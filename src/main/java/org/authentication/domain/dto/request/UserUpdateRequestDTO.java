package org.authentication.domain.dto.request;

public record UserUpdateRequestDTO(
        String name,
        String email,
        String password
) {}
