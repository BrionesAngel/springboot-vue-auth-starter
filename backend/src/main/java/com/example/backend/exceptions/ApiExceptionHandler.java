package com.example.backend.exceptions;

import java.time.LocalDateTime;

import javax.security.sasl.AuthenticationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {
    return ResponseEntity.status(status)
        .body(new ErrorResponse(message, status.value(), LocalDateTime.now()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    log.warn("Resource not found: {}", ex.getMessage());
    return buildError(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
    log.warn("User not found: {}", ex.getMessage());
    return buildError(HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
  }

  @ExceptionHandler(DuplicateEmailException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex) {
    log.warn("Duplicate email: {}", ex.getMessage());
    return buildError(HttpStatus.CONFLICT, "DUPLICATE_EMAIL");
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    log.warn("Bad credentials: {}", ex.getMessage());
    return buildError(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));
    log.warn("Validation error: {}", message);
    return buildError(HttpStatus.BAD_REQUEST, message);
  }

  @ExceptionHandler(InternalAuthenticationServiceException.class)
  public ResponseEntity<ErrorResponse> handleInternalAuth(InternalAuthenticationServiceException ex) {
    log.warn("Auth error: {}", ex.getMessage());
    return buildError(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
    log.warn("Authentication Exception: {}", ex.getMessage());
    return buildError(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS");
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    log.error("UNHANDLED ERROR: ", ex);
    return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
  }
}
