package com.indiana.service.inventory.inventory.application.sort;

import lombok.Getter;

@Getter
public enum InventorySortBy {
  ID("id"),
  QUALITY_AVAILABLE("quantityAvailable"),
  MINIMUM_STOCK_LEVEL("minimumStockLevel"),
  MAXIMUM_STOCK_LEVEL("maximumStockLevel"),
  REORDER_POINT("reorderPoint"),
  PRODUCT_ID("productId"),
  WAREHOUSE_ID("warehouseId");

  private final String value;

  InventorySortBy(final String value) {
    this.value = value;
  }

  public static InventorySortBy fromValue(final String value) {
    for (final InventorySortBy sortBy : InventorySortBy.values()) {
      if (sortBy.getValue().equalsIgnoreCase(value)) {
        return sortBy;
      }
    }
    return ID;
  }
}
