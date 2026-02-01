package org.authentication.domain.dto.response;

import org.authentication.domain.entity.User;
import org.authentication.domain.enums.UserRole;

public record UserResponseDTO(
        Long idUser,
        String name,
        String email,
        UserRole role
) {
    public UserResponseDTO(User user) {
        this(
            user.getIdUser(),
            user.getName(),
            user.getEmail(),
            user.getRole()
        );
    }
}
