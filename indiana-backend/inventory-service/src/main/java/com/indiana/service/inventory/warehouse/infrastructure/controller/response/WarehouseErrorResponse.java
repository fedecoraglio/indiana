package com.indiana.service.inventory.warehouse.infrastructure.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WarehouseErrorResponse {

  private final String code;
  private final String message;
  private final String detail;
  private final Integer statusCode;
}
