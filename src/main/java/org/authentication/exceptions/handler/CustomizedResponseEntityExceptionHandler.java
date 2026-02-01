package org.authentication.exceptions.handler;

import org.authentication.domain.dto.response.MessageResponseDTO;
import org.authentication.exceptions.DataIntegrityViolationException;
import org.authentication.exceptions.ExceptionResponse;
import org.authentication.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        MessageResponseDTO messageResponse = new MessageResponseDTO("error", "Erro", List.of(ex.getMessage()));
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
        var messageResponse = new MessageResponseDTO("error", "Erro", List.of(ex.getMessage()));
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCredentialsException() {
        var messageResponse = new MessageResponseDTO("error", "Erro", List.of("Usuário ou senha inválidos"));
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}
