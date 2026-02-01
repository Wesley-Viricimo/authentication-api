package org.authentication.domain.dto.response;

import org.authentication.domain.enums.UserRole;

public record UserResponseDTO(
        Long idUser,
        String name,
        String email,
        UserRole role
) {}
