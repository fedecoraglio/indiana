package com.indiana.inventory.api.product.infrastructure.controller.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.indiana.inventory.api.product.application.exception.ProductException;
import com.indiana.inventory.api.product.application.exception.ProductException.ProductExceptionType;
import com.indiana.inventory.api.product.infrastructure.controller.response.ErrorResponse;
import com.indiana.inventory.api.product.infrastructure.exception.ProductInfraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleArgumentNotValidExceptions(
      final MethodArgumentNotValidException ex) {
    log.error("Argument Json not valid error: {}", ex.getMessage());
    if (ex.getBindingResult().hasErrors()) {
      final ObjectError error = ex.getBindingResult().getAllErrors().getFirst();
      final ErrorResponse errorResponse = new ErrorResponse(
          "VALIDATION_ERROR", error.getDefaultMessage(), error.getDefaultMessage(),
          BAD_REQUEST.value());
      return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    return handleProductInfraException(
        new ProductInfraException(new ProductException(ProductExceptionType.PRODUCT_UNEXPECTED_ERROR)));
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(ProductInfraException.class)
  public ResponseEntity<ErrorResponse> handleProductInfraException(final ProductInfraException ex) {
    log.error("Product infra error: {}", ex.getMessage());
    final ErrorResponse errorResponse = new ErrorResponse(
        ex.getProductException().getType().getCode(),
        ex.getProductException().getType().getMessage(), ex.getMessage(), BAD_REQUEST.value());
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }
}
