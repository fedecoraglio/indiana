package com.indiana.product.api.inventory.application.exception;

import lombok.Getter;

@Getter
public class InventoryException extends RuntimeException {

  private InventoryExceptionType type;

  public InventoryException(final InventoryExceptionType type) {
    super(type.getMessage());
    this.type = type;
  }

  public InventoryException(final InventoryExceptionType type, final String message) {
    super(message != null ? message : type.getMessage());
    this.type = type;
  }

  public InventoryException(final InventoryExceptionType type, final Throwable cause) {
    super(type.getMessage(), cause);
  }

  @Getter
  public enum InventoryExceptionType {
    INVENTORY_CANNOT_BE_NULL("inventory-1000", "Inventory cannot be null"),
    INVENTORY_UNEXPECTED_ERROR("inventory-1001", "Unexpected error saving inventory"),
    INVENTORY_SIZE_PAGINATION_LIMIT("inventory-1002", "Pagination limit exceed");

    private final String code;
    private final String message;

    InventoryExceptionType(final String code, final String message) {
      this.code = code;
      this.message = message;
    }
  }
}