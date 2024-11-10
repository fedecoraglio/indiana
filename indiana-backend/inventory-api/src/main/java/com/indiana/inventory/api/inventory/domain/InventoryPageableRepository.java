package com.indiana.inventory.api.inventory.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryPageableRepository extends PagingAndSortingRepository<Inventory, Long> {

  Page<Inventory> findByProductId(final Long productId, Pageable pageable);
}
