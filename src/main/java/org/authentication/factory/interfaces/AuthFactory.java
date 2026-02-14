package org.authentication.factory.interfaces;

import org.authentication.domain.dto.request.AuthRequestDTO;
import org.authentication.domain.dto.response.AuthResponseDTO;
import org.authentication.domain.entity.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthFactory {
    AuthResponseDTO createToken(AuthRequestDTO data);
    AuthResponseDTO createRefreshToken(UserDetails userDetails, String refreshToken);
    RefreshToken validateRefreshToken(String token);
}