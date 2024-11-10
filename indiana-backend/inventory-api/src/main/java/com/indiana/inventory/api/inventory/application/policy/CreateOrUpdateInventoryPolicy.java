package com.indiana.inventory.api.inventory.application.policy;

import com.indiana.inventory.api.inventory.application.dto.InventoryDto;
import com.indiana.inventory.api.inventory.application.exception.InventoryException;
import com.indiana.inventory.api.inventory.application.exception.InventoryException.InventoryExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreateOrUpdateInventoryPolicy {

  public void check(final InventoryDto inventoryDto) {
    if (inventoryDto == null) {
      throw new InventoryException(InventoryExceptionType.INVENTORY_CANNOT_BE_NULL);
    }
    if (inventoryDto.getQuantityAvailable() < 0) {
      throw new InventoryException(InventoryExceptionType.INVENTORY_QUANTITY_CANNOT_BE_NEGATIVE);
    }
    if (inventoryDto.getProductId() == null) {
      throw new InventoryException(InventoryExceptionType.INVENTORY_PRODUCT_CANNOT_BE_NULL);
    }
    if (inventoryDto.getWarehouseId() == null) {
      throw new InventoryException(InventoryExceptionType.INVENTORY_WAREHOUSE_CANNOT_BE_NULL);
    }
  }
}
