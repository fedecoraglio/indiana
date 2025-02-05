package com.indiana.service.inventory.warehouse.application.exception;

import lombok.Getter;

@Getter
public class WarehouseException extends RuntimeException {

  private WarehouseExceptionType type;

  public WarehouseException(final WarehouseExceptionType type) {
    super(type.getMessage());
    this.type = type;
  }

  public WarehouseException(final WarehouseExceptionType type, final String message) {
    super(message != null ? message : type.getMessage());
    this.type = type;
  }

  public WarehouseException(final WarehouseExceptionType type, final Throwable cause) {
    super(type.getMessage(), cause);
  }

  @Getter
  public enum WarehouseExceptionType {
    WAREHOUSE_CANNOT_BE_NULL("warehouse-1", "Warehouse cannot be null"),
    WAREHOUSE_UNEXPECTED_ERROR("warehouse-2", "Unexpected error on location"),
    WAREHOUSE_SIZE_PAGINATION_LIMIT("warehouse-3", "Pagination limit exceed"),
    WAREHOUSE_SAVING_ERROR("warehouse-4", "Error saving location"),
    WAREHOUSE_NAME_CANNOT_BE_NULL("warehouse-5", "Name is required"),
    WAREHOUSE_LOCATION_ID_CANNOT_BE_NULL("warehouse-5", "Location is required");

    private final String code;
    private final String message;

    WarehouseExceptionType(final String code, final String message) {
      this.code = code;
      this.message = message;
    }
  }
}
