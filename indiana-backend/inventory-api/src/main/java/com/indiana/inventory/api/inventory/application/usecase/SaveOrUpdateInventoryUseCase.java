package com.indiana.inventory.api.inventory.application.usecase;

import com.indiana.inventory.api.inventory.application.dto.InventoryDto;
import com.indiana.inventory.api.inventory.application.exception.InventoryException;
import com.indiana.inventory.api.inventory.application.exception.InventoryException.InventoryExceptionType;
import com.indiana.inventory.api.inventory.application.mapper.InventoryMapper;
import com.indiana.inventory.api.inventory.application.policy.CreateOrUpdateInventoryPolicy;
import com.indiana.inventory.api.inventory.domain.Inventory;
import com.indiana.inventory.api.inventory.domain.InventoryRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Component
public class SaveOrUpdateInventoryUseCase {

  private final InventoryRepository inventoryRepository;
  private final InventoryMapper inventoryMapper;
  private final CreateOrUpdateInventoryPolicy createOrUpdateInventoryPolicy;

  @Transactional
  public InventoryDto execute(InventoryDto inventoryDto) {
    try {
      log.info("Starting updating inventory");
      createOrUpdateInventoryPolicy.check(inventoryDto);
      final Inventory inventoryToSave = createInventoryToSave(inventoryDto);
      final Inventory inventorySaved = inventoryRepository.save(inventoryToSave);
      log.info("Ending updating inventory. Id: {}", inventorySaved.getId());
      return inventoryMapper.toInventoryDto(inventorySaved);
    } catch (final InventoryException e) {
      log.error("Error updating inventory", e);
      throw e;
    } catch (final Exception e) {
      throw new InventoryException(InventoryExceptionType.INVENTORY_SAVING_ERROR, e);
    }
  }

  private Inventory createInventoryToSave(final InventoryDto inventoryDto) {
    final Inventory inventoryToSave = inventoryMapper.toInventory(inventoryDto);
    final Optional<Inventory> optionalInventory =
        inventoryRepository.findAllByWarehouseIdAndProductId(inventoryToSave.getWarehouseId(),
            inventoryToSave.getProductId());
    if (optionalInventory.isPresent()) {
      final Inventory currentInventory = optionalInventory.get();
      inventoryToSave.setId(currentInventory.getId());
      inventoryToSave.setQuantityAvailable(
          currentInventory.getQuantityAvailable() + inventoryToSave.getQuantityAvailable());
    }
    return inventoryToSave;
  }
}
