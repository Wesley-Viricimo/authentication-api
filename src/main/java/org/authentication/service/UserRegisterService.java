package org.authentication.service;

import jakarta.transaction.Transactional;
import org.authentication.domain.dto.request.UserCreateRequestDTO;
import org.authentication.domain.dto.response.MessageResponseDTO;
import org.authentication.domain.dto.response.ResponseDataDTO;
import org.authentication.domain.dto.response.UserResponseDTO;
import org.authentication.domain.entity.User;
import org.authentication.domain.enums.UserRole;
import org.authentication.factory.interfaces.UserRegisterFactory;
import org.authentication.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegisterService {
    private final UserRepository userRepository;
    private final UserRegisterFactory userRegisterFactory;

    public UserRegisterService(UserRepository userRepository, UserRegisterFactory userRegisterFactory) {
        this.userRepository = userRepository;
        this.userRegisterFactory = userRegisterFactory;
    }

    @Transactional
    public ResponseDataDTO<UserResponseDTO> createUser(UserCreateRequestDTO userCreateRequestDTO, UserRole userRole) {
        User user = userRepository.save(this.userRegisterFactory.createUser(userCreateRequestDTO, userRole));
        UserResponseDTO userDTO = new UserResponseDTO(user);
        MessageResponseDTO messageResponse = new MessageResponseDTO("success", "Sucesso", List.of("Usuário cadastrado com sucesso"));
        return new ResponseDataDTO<>(userDTO, messageResponse, HttpStatus.CREATED.value());
    }
}
