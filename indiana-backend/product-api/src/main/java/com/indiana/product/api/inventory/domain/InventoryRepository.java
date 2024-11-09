package com.indiana.product.api.inventory.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Long>  {

  List<Inventory> findByProductId(final Long productId);

}
