package com.indiana.service.inventory.provider.infrastructure.controller.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.indiana.service.inventory.product.infrastructure.exception.ProductInfraException;
import com.indiana.service.inventory.provider.application.exception.ProviderException;
import com.indiana.service.inventory.provider.application.exception.ProviderException.ProviderExceptionType;
import com.indiana.service.inventory.common.response.ErrorResponse;
import com.indiana.service.inventory.provider.infrastructure.exception.ProviderInfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ProviderControllerAdvice {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleArgumentNotValidExceptions(
      final MethodArgumentNotValidException ex) {
    log.error("Provider Argument Json not valid error: {}", ex.getMessage());
    if (ex.getBindingResult().hasErrors()) {
      final ObjectError error = ex.getBindingResult().getAllErrors().getFirst();
      final ErrorResponse errorResponse = new ErrorResponse(
          "VALIDATION_ERROR", error.getDefaultMessage(), error.getDefaultMessage(),
          BAD_REQUEST.value());
      return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    return handleInfraException(new ProviderInfraException(
        new ProviderException(ProviderExceptionType.PROVIDER_UNEXPECTED_ERROR)));
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(ProductInfraException.class)
  public ResponseEntity<ErrorResponse> handleInfraException(final ProviderInfraException ex) {
    log.error("Provider infra error: {}", ex.getMessage());
    final ErrorResponse errorResponse = new ErrorResponse(
        ex.getProviderException().getType().getCode(),
        ex.getProviderException().getType().getMessage(), ex.getMessage(), BAD_REQUEST.value());
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(ProviderException.class)
  public ResponseEntity<ErrorResponse> handleProviderException(final ProviderException ex) {
    log.error("Provider exception error: {}", ex.getMessage());
    final ErrorResponse errorResponse = new ErrorResponse(ex.getType().getCode(),
        ex.getType().getMessage(), ex.getMessage(), BAD_REQUEST.value());
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }
}
