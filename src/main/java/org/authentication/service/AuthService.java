package org.authentication.service;

import org.authentication.domain.dto.request.AuthRequestDTO;
import org.authentication.domain.dto.response.AuthResponseDTO;
import org.authentication.domain.dto.response.MessageResponseDTO;
import org.authentication.domain.dto.response.ResponseDataDTO;
import org.authentication.domain.entity.User;
import org.authentication.factory.interfaces.AuthFactory;
import org.authentication.repository.UserRepository;
import org.authentication.security.UserSecurity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthFactory authFactory;

    public AuthService(UserRepository userRepository, AuthFactory authFactory) {
        this.userRepository = userRepository;
        this.authFactory = authFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new UserSecurity(user);
    }

    public ResponseDataDTO<AuthResponseDTO> authenticate(AuthRequestDTO data) {
        AuthResponseDTO authResponse = new AuthResponseDTO(this.authFactory.createToken(data));
        MessageResponseDTO message = new MessageResponseDTO("success", "Sucesso", List.of("Autenticação realizada com sucesso"));
        return new ResponseDataDTO<>(authResponse, message, HttpStatus.OK.value());
    }
}
