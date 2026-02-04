package org.authentication.factory.impl;

import org.authentication.domain.dto.request.AuthRequestDTO;
import org.authentication.domain.entity.User;
import org.authentication.exceptions.UserInactiveException;
import org.authentication.factory.interfaces.AuthFactory;
import org.authentication.repository.UserRepository;
import org.authentication.security.UserSecurity;
import org.authentication.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthFactoryImpl implements AuthFactory {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthFactoryImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public String createToken(AuthRequestDTO data) {
        User user = this.userRepository.findByEmail(data.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não está cadastrado no sistema"));

        if (!user.isActive())
            throw new UserInactiveException("Usuário não está ativo. Por favor, verifique seu e-mail para ativar sua conta.");

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var userSecurity = (UserSecurity) auth.getPrincipal();
        return this.jwtTokenProvider.createToken(userSecurity);
    }
}
