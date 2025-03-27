package com.indiana.service.inventory.inventory.application.mapper;

import com.indiana.service.inventory.inventory.application.dto.InventoryDto;
import com.indiana.service.inventory.inventory.domain.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

  public InventoryDto toInventoryDto(final Inventory inventory) {
    if (inventory == null) {
      return null;
    }
    return InventoryDto.builder().id(inventory.getId())
        .minimumStockLevel(inventory.getMinimumStockLevel())
        .maximumStockLevel(inventory.getMaximumStockLevel()).productId(inventory.getProductId())
        .quantityAvailable(inventory.getQuantityAvailable())
        .reorderPoint(inventory.getReorderPoint())
        .warehouseId(inventory.getWarehouseId())
        .productId(inventory.getProductId())
        .build();
  }

  public Inventory toInventory(final InventoryDto inventoryDto) {
    if (inventoryDto == null) {
      return null;
    }
    final Inventory inventory = new Inventory();
    inventory.setId(inventoryDto.getId());
    inventory.setMinimumStockLevel(inventoryDto.getMinimumStockLevel());
    inventory.setMaximumStockLevel(inventoryDto.getMaximumStockLevel());
    inventory.setQuantityAvailable(inventoryDto.getQuantityAvailable());
    inventory.setReorderPoint(inventoryDto.getReorderPoint());
    inventory.setProductId(inventoryDto.getProductId());
    inventory.setWarehouseId(inventoryDto.getWarehouseId());
    return inventory;
  }
}
