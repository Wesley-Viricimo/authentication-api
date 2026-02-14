package org.authentication.factory.impl;

import jakarta.transaction.Transactional;
import org.authentication.domain.dto.request.AuthRequestDTO;
import org.authentication.domain.dto.response.AuthResponseDTO;
import org.authentication.domain.entity.RefreshToken;
import org.authentication.domain.entity.User;
import org.authentication.exceptions.NotFoundException;
import org.authentication.exceptions.UserInactiveException;
import org.authentication.factory.interfaces.AuthFactory;
import org.authentication.repository.RefreshTokenRepository;
import org.authentication.repository.UserRepository;
import org.authentication.security.AuthenticationFacade;
import org.authentication.security.UserSecurity;
import org.authentication.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuthFactoryImpl implements AuthFactory {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationFacade authenticationFacade;
    private final AuthenticationManager authenticationManager;

    @Value("${api.security.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public AuthFactoryImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, JwtTokenProvider jwtTokenProvider, AuthenticationFacade authenticationFacade, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationFacade = authenticationFacade;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponseDTO createToken(AuthRequestDTO data) {
        User user = this.userRepository.findByEmail(data.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não está cadastrado no sistema"));

        if (!user.isActive())
            throw new UserInactiveException("Usuário não está ativo. Por favor, verifique seu e-mail para ativar sua conta.");

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        UserSecurity userSecurity = (UserSecurity) auth.getPrincipal();

        String accessToken = this.jwtTokenProvider.createToken(userSecurity);
        this.invalidateRefreshToken(user);
        String refreshToken = createRefreshToken(user);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthResponseDTO createRefreshToken(UserDetails userDetails, String refreshToken) {
        UserSecurity userSecurity = (UserSecurity) userDetails;
        String newAccessToken = this.jwtTokenProvider.createToken(userSecurity);
        String newRefreshToken = this.refreshToken(refreshToken);

        return new AuthResponseDTO(newAccessToken, newRefreshToken);
    }

    private String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);

        RefreshToken refreshToken = new RefreshToken(token, user, expiryDate);
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    private String refreshToken(String oldRefreshToken) {
        RefreshToken refreshToken = validateRefreshToken(oldRefreshToken);

        refreshToken.setRevoked(true);
        this.refreshTokenRepository.save(refreshToken);

        return createRefreshToken(refreshToken.getUser());
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = this.refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Refresh token não encontrado"));

        if (refreshToken.isRevoked()) throw new NotFoundException("Refresh token foi revogado");
        if (refreshToken.isExpired()) throw new NotFoundException("Refresh token expirou");

        return refreshToken;
    }

    @Transactional
    public void invalidateRefreshToken() {
        User user = this.userRepository.findById(this.authenticationFacade.getAuthenticatedUserId())
                .orElseThrow(() -> new NotFoundException("Usuário autenticado não encontrado"));

        this.refreshTokenRepository.revokeAllActiveTokensByUser(user);
    }

    @Transactional
    private void invalidateRefreshToken(User user) {
        this.refreshTokenRepository.revokeAllActiveTokensByUser(user);
    }
}
