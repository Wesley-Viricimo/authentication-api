package org.authentication.service;

import org.authentication.domain.dto.request.AuthRequestDTO;
import org.authentication.domain.dto.request.RefreshTokenRequestDTO;
import org.authentication.domain.dto.response.AuthResponseDTO;
import org.authentication.domain.dto.response.MessageResponseDTO;
import org.authentication.domain.dto.response.ResponseDataDTO;
import org.authentication.domain.entity.RefreshToken;
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
        AuthResponseDTO authResponse = this.authFactory.createToken(data);
        MessageResponseDTO message = new MessageResponseDTO("success", "Sucesso", List.of("Autenticação realizada com sucesso"));
        return new ResponseDataDTO<>(authResponse, message, HttpStatus.OK.value());
    }

    public ResponseDataDTO<AuthResponseDTO> refreshToken(RefreshTokenRequestDTO data) {
        RefreshToken refreshToken = this.authFactory.validateRefreshToken(data.refreshToken());
        UserDetails userDetails = loadUserByUsername(refreshToken.getUser().getEmail());
        AuthResponseDTO refreshResponse = this.authFactory.createRefreshToken(userDetails, data.refreshToken());
        MessageResponseDTO message = new MessageResponseDTO("success", "Sucesso", List.of("Token atualizado com sucesso"));
        return new ResponseDataDTO<>(refreshResponse, message, HttpStatus.OK.value());
    }
}
