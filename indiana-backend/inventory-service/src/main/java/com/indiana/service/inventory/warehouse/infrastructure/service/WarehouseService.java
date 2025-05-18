package com.indiana.service.inventory.warehouse.infrastructure.service;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.infrastructure.service.LocationService;
import com.indiana.service.inventory.product.application.exception.ProductException;
import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import com.indiana.service.inventory.product.infrastructure.exception.ProductInfraException;
import com.indiana.service.inventory.warehouse.application.dto.WarehouseDetailDto;
import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException.WarehouseExceptionType;
import com.indiana.service.inventory.warehouse.application.usecase.GetWarehouseByIdUseCase;
import com.indiana.service.inventory.warehouse.application.usecase.SaveOrUpdateWarehouseUseCase;
import com.indiana.service.inventory.warehouse.application.usecase.SearchWarehouseUseCase;
import com.indiana.service.inventory.warehouse.infrastructure.exception.WarehouseInfraException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
  private final LocationService locationService;

  public WarehouseDto saveOrUpdate(final WarehouseDto dto) {
    try {
      return saveOrUpdateWarehouseUseCase.execute(dto);
    } catch (final WarehouseException exception) {
      throw new WarehouseInfraException(exception);
    }
  }

  public WarehouseDetailDto getById(final Long id) {
    try {
      final WarehouseDto dto = getWarehouseByIdUseCase.execute(id);
      if (dto == null) {
        return null;
      }
      final LocationDto locationDto = locationService.getById(dto.locationId());
      return WarehouseDetailDto.builder().id(dto.id()).name(dto.name())
          .location(locationDto)
          .build();
    } catch (final WarehouseException exception) {
      throw new WarehouseInfraException(exception);
    }
  }

  public Page<WarehouseDetailDto> search(final String query, final Integer page,
      final Integer size, final String sortBy, final String sortOrder) {
    try {
      if (size > 300) {
        throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_SIZE_PAGINATION_LIMIT);
      }
      final PageRequest pageRequest = getPageRequest(page, size, sortBy, sortOrder);
      final Page<WarehouseDto> pageDto = searchWarehouseUseCase.execute(query, pageRequest);

      // Processing results
      final List<Long> locationIds = pageDto.getContent().parallelStream().map(
          WarehouseDto::locationId).toList();
      final List<LocationDto> locations = locationService.getAllByIds(locationIds);
      final List<WarehouseDetailDto> resultDetail = new ArrayList<>(pageDto.getNumberOfElements());
      pageDto.getContent().forEach(dto -> {
        final LocationDto locationDto = locations.stream()
            .filter(l -> l.getId().equals(dto.locationId())).findFirst().orElse(null);
        resultDetail.add(WarehouseDetailDto.builder().id(dto.id()).name(dto.name())
            .location(locationDto).build());
      });
      return new PageImpl<>(resultDetail, pageDto.getPageable(), pageDto.getTotalElements());
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
