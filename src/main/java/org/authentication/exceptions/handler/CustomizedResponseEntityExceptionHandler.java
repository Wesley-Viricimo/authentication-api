package org.authentication.exceptions.handler;

import org.authentication.domain.dto.response.MessageResponseDTO;
import org.authentication.exceptions.DataIntegrityViolationException;
import org.authentication.exceptions.ExceptionResponse;
import org.authentication.exceptions.NotFoundException;
import org.authentication.exceptions.UserInactiveException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        MessageResponseDTO messageResponse = new MessageResponseDTO("error", "Erro", List.of(ex.getMessage()));
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCredentialsException() {
        MessageResponseDTO messageResponse = new MessageResponseDTO("error", "Erro", List.of("Usuário ou senha inválidos"));
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserInactiveException.class)
    public final ResponseEntity<ExceptionResponse> handleUserInactiveException(UserInactiveException ex) {
        MessageResponseDTO messageResponse = new MessageResponseDTO("error", "Erro", List.of(ex.getMessage()));
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.FORBIDDEN.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorDetails = List.of("Formato de requisição inválido. Verifique o JSON enviado");
        MessageResponseDTO messageResponse = new MessageResponseDTO("error", "Erro", errorDetails);
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorDetails = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        MessageResponseDTO messageResponse = new MessageResponseDTO("error", "Erro", errorDetails);
        ExceptionResponse exceptionResponse = new ExceptionResponse(messageResponse, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
