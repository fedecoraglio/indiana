package com.indiana.service.inventory.location.infrastructure.controller.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.indiana.service.inventory.location.application.exception.LocationException;
import com.indiana.service.inventory.location.application.exception.LocationException.LocationExceptionType;
import com.indiana.service.inventory.location.infrastructure.controller.response.ErrorResponse;
import com.indiana.service.inventory.location.infrastructure.exception.LocationInfraException;
import com.indiana.service.inventory.product.infrastructure.exception.ProductInfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LocationControllerAdvice {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleArgumentNotValidExceptions(
      final MethodArgumentNotValidException ex) {
    log.error("Location Argument Json not valid error: {}", ex.getMessage());
    if (ex.getBindingResult().hasErrors()) {
      final ObjectError error = ex.getBindingResult().getAllErrors().getFirst();
      final ErrorResponse errorResponse = new ErrorResponse(
          "VALIDATION_ERROR", error.getDefaultMessage(), error.getDefaultMessage(),
          BAD_REQUEST.value());
      return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    return handleInfraException(new LocationInfraException(
        new LocationException(LocationExceptionType.LOCATION_UNEXPECTED_ERROR)));
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(ProductInfraException.class)
  public ResponseEntity<ErrorResponse> handleInfraException(final LocationInfraException ex) {
    log.error("Product infra error: {}", ex.getMessage());
    final ErrorResponse errorResponse = new ErrorResponse(
        ex.getLocationException().getType().getCode(),
        ex.getLocationException().getType().getMessage(), ex.getMessage(), BAD_REQUEST.value());
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }
}
