package com.indiana.service.inventory.warehouse.infrastructure.exception;

import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import lombok.Getter;

@Getter
public class WarehouseInfraException extends RuntimeException {

  private final WarehouseException warehouseException;

  public WarehouseInfraException(final WarehouseException warehouseException) {
    super(warehouseException.getMessage(), warehouseException);
    this.warehouseException = warehouseException;
  }
}
