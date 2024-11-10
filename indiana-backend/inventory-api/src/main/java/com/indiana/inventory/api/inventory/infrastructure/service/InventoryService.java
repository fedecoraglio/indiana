package com.indiana.inventory.api.inventory.infrastructure.service;

import com.indiana.inventory.api.inventory.application.exception.InventoryException;
import com.indiana.inventory.api.inventory.application.usecase.SaveOrUpdateInventoryUseCase;
import com.indiana.inventory.api.inventory.application.dto.InventoryDto;
import com.indiana.inventory.api.inventory.infrastructure.exception.InventoryInfraException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class InventoryService {

  private final SaveOrUpdateInventoryUseCase saveOrUpdateInventoryUseCase;

  public InventoryDto createOrUpdateInventory(InventoryDto inventoryDto){
    try {
      return saveOrUpdateInventoryUseCase.execute(inventoryDto);
    } catch (final InventoryException inventoryException) {
      throw new InventoryInfraException(inventoryException);
    }
  }
}
