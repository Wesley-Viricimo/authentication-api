package org.authentication.factory.interfaces;

import org.authentication.domain.dto.request.UserCreateRequestDTO;
import org.authentication.domain.entity.User;
import org.authentication.domain.enums.UserRole;

public interface UserRegisterFactory {
    User createUser(UserCreateRequestDTO userCreateRequestDTO, UserRole userRole);
}
