package org.authentication.domain.dto.response;

import java.io.Serializable;

public record ResponseDataDTO<T>(
        T data,
        MessageResponseDTO message,
        Integer statusCode
) implements Serializable {
    public ResponseDataDTO(T data, MessageResponseDTO message, Integer statusCode ) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }
}