package org.authentication.domain.dto.response;

import java.util.List;

public record MessageResponseDTO(
        String severity,
        String message,
        List<String> details
) {}
