package com.indiana.service.inventory.warehouse.infrastructure.controller.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.indiana.service.inventory.product.infrastructure.exception.ProductInfraException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException.WarehouseExceptionType;
import com.indiana.service.inventory.warehouse.infrastructure.controller.response.WarehouseErrorResponse;
import com.indiana.service.inventory.warehouse.infrastructure.exception.WarehouseInfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WarehouseControllerAdvice {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<WarehouseErrorResponse> handleArgumentNotValidExceptions(
      final MethodArgumentNotValidException ex) {
    log.error("Location Argument Json not valid error: {}", ex.getMessage());
    if (ex.getBindingResult().hasErrors()) {
      final ObjectError error = ex.getBindingResult().getAllErrors().getFirst();
      final WarehouseErrorResponse errorResponse = new WarehouseErrorResponse(
          "VALIDATION_ERROR", error.getDefaultMessage(), error.getDefaultMessage(),
          BAD_REQUEST.value());
      return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    return handleInfraException(new WarehouseInfraException(
        new WarehouseException(WarehouseExceptionType.WAREHOUSE_UNEXPECTED_ERROR)));
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(ProductInfraException.class)
  public ResponseEntity<WarehouseErrorResponse> handleInfraException(final WarehouseInfraException ex) {
    log.error("Product infra error: {}", ex.getMessage());
    final WarehouseErrorResponse errorResponse = new WarehouseErrorResponse(
        ex.getWarehouseException().getType().getCode(),
        ex.getWarehouseException().getType().getMessage(), ex.getMessage(), BAD_REQUEST.value());
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }
}
