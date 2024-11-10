package com.indiana.inventory.api.inventory.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class InventoryDto {

  private final Long id;
  private final Long quantityAvailable;
  private final Long minimumStockLevel;
  private final Long maximumStockLevel;
  private final Long reorderPoint;
  private final Long productId;
  private final Long warehouseId;
}
