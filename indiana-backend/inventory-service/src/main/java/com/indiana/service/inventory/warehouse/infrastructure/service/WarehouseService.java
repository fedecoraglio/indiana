package com.indiana.service.inventory.warehouse.infrastructure.service;

import com.indiana.service.inventory.product.application.exception.ProductException;
import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import com.indiana.service.inventory.product.infrastructure.exception.ProductInfraException;
import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException.WarehouseExceptionType;
import com.indiana.service.inventory.warehouse.application.usecase.GetWarehouseByIdUseCase;
import com.indiana.service.inventory.warehouse.application.usecase.SaveOrUpdateWarehouseUseCase;
import com.indiana.service.inventory.warehouse.application.usecase.SearchWarehouseUseCase;
import com.indiana.service.inventory.warehouse.infrastructure.exception.WarehouseInfraException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class WarehouseService {

  private final SaveOrUpdateWarehouseUseCase saveOrUpdateWarehouseUseCase;
  private final GetWarehouseByIdUseCase getWarehouseByIdUseCase;
  private final SearchWarehouseUseCase searchWarehouseUseCase;

  public WarehouseDto saveOrUpdate(final WarehouseDto dto) {
    try {
      return saveOrUpdateWarehouseUseCase.execute(dto);
    } catch (final WarehouseException exception) {
      throw new WarehouseInfraException(exception);
    }
  }

  public WarehouseDto getById(final Long id) {
    try {
      return getWarehouseByIdUseCase.execute(id);
    } catch (final WarehouseException exception) {
      throw new WarehouseInfraException(exception);
    }
  }

  public Page<WarehouseDto> search(final String query, final Integer page,
      final Integer size, final String sortBy, final String sortOrder) {
    try {
      if(size > 300) {
        throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_SIZE_PAGINATION_LIMIT);
      }
      final PageRequest pageRequest = getPageRequest(page, size, sortBy, sortOrder);
      return searchWarehouseUseCase.execute(query, pageRequest);
    } catch (final ProductException e) {
      log.error("Error searching product", e);
      throw new ProductInfraException(e);
    }
  }

  //TODO: move this method to a new class.
  private PageRequest getPageRequest(final Integer page, final Integer size, final String sortBy,
      final String sortOrder) {
    if (page == null || size == null) {
      return null;
    }
    final Sort sort = Sort.by(Direction.fromOptionalString(sortOrder).orElse(Direction.DESC),
        sortBy != null ? sortBy : ProductSortBy.ID.getValue());
    return PageRequest.of(page, size, sort);
  }
}
