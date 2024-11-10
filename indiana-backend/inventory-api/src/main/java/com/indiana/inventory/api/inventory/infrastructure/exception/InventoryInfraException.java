package com.indiana.inventory.api.inventory.infrastructure.exception;

import com.indiana.inventory.api.inventory.application.exception.InventoryException;
import lombok.Getter;

@Getter
public class InventoryInfraException extends RuntimeException {

  private final InventoryException inventoryException;

  public InventoryInfraException(final InventoryException inventoryException) {
    super(inventoryException.getMessage(), inventoryException);
    this.inventoryException = inventoryException;
  }
}
