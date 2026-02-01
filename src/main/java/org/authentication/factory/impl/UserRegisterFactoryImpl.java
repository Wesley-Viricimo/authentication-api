package org.authentication.factory.impl;

import org.authentication.domain.dto.request.UserCreateRequestDTO;
import org.authentication.domain.entity.User;
import org.authentication.domain.enums.UserRole;
import org.authentication.exceptions.DataIntegrityViolationException;
import org.authentication.factory.interfaces.UserRegisterFactory;
import org.authentication.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserRegisterFactoryImpl implements UserRegisterFactory {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserRegisterFactoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(UserCreateRequestDTO userCreateRequestDTO, UserRole role) {
        Optional<User> existingUser = userRepository.findByEmail(userCreateRequestDTO.email());

        if (existingUser.isPresent())
            throw new DataIntegrityViolationException(String.format("Email '%s' já cadastrado no sistema", userCreateRequestDTO.email()));

        return new User(
                userCreateRequestDTO.name(),
                role,
                userCreateRequestDTO.email(),
                this.passwordEncoder.encode(userCreateRequestDTO.password())
        );
    }
}
