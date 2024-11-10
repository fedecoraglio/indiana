package com.indiana.inventory.api.inventory.infrastructure.service;

import com.indiana.inventory.api.inventory.application.exception.InventoryException;
import com.indiana.inventory.api.inventory.application.usecase.CreateOrUpdateInventoryUseCase;
import com.indiana.inventory.api.inventory.application.dto.InventoryDto;
import com.indiana.inventory.api.inventory.infrastructure.exception.InventoryInfraException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class InventoryService {

  private final CreateOrUpdateInventoryUseCase createOrUpdateInventoryUseCase;

  public InventoryDto createOrUpdateInventory(InventoryDto inventoryDto){
    try {
      return createOrUpdateInventoryUseCase.execute(inventoryDto);
    } catch (final InventoryException inventoryException) {
      throw new InventoryInfraException(inventoryException);
    }
  }
}
