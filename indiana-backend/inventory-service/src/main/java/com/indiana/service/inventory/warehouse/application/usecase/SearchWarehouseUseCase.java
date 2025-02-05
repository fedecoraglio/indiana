package com.indiana.service.inventory.warehouse.application.usecase;

import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException.WarehouseExceptionType;
import com.indiana.service.inventory.warehouse.application.mapper.WarehouseMapper;
import com.indiana.service.inventory.warehouse.domain.Warehouse;
import com.indiana.service.inventory.warehouse.domain.WarehousePageableRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SearchWarehouseUseCase {

  private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 20,
      Sort.by(ProductSortBy.ID.getValue()).descending());
  private final WarehousePageableRepository warehousePageableRepository;
  private final WarehouseMapper warehouseMapper;

  public Page<WarehouseDto> execute(final String query, final PageRequest pageRequest) {
    try {
      final PageRequest pageable = pageRequest != null ? pageRequest : PAGE_REQUEST;
      Page<Warehouse> page;
      if (query == null || query.isEmpty()) {
        page = warehousePageableRepository.findAll(pageable);
      } else {
        page = warehousePageableRepository.findByNameIgnoreCaseContaining(query, pageable);
      }
      return page.map(warehouseMapper::toLocationDto);
    } catch (final WarehouseException e) {
      log.error("Error getting location", e);
      throw e;
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
      throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_UNEXPECTED_ERROR, e);
    }
  }
}
