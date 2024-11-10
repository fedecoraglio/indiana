package com.indiana.inventory.api.product.infrastructure.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

  private final String code;
  private final String message;
  private final String detail;
  private final Integer statusCode;
}
