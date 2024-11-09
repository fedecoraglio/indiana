package com.indiana.product.api.inventory.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class InventoryDto {

  private final Long id;
  private final Integer quantityAvailable;
  private final Integer minimumStockLevel;
  private final Integer maximumStockLevel;
  private final Integer reorderPoint;
  private final Long productId;
  private final Long warehouseId;
}
