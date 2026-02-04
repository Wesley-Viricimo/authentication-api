package org.authentication.factory.interfaces;

import org.authentication.domain.dto.request.AuthRequestDTO;

public interface AuthFactory {
    String createToken(AuthRequestDTO data);
}