package com.indiana.service.inventory.warehouse.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehousePageableRepository extends PagingAndSortingRepository<Warehouse, Long> {

  Page<Warehouse> findByNameIgnoreCaseContaining(final String name, final Pageable pageable);
}
