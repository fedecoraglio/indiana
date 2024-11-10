package com.indiana.inventory.api.inventory.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

  Optional<Inventory> findAllByWarehouseIdAndProductId(final Long warehouseId, final Long productId);

}
