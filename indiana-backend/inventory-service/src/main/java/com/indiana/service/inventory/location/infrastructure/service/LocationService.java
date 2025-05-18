package com.indiana.service.inventory.location.infrastructure.service;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.application.exception.LocationException;
import com.indiana.service.inventory.location.application.exception.LocationException.LocationExceptionType;
import com.indiana.service.inventory.location.application.usecase.GetLocationByIdUseCase;
import com.indiana.service.inventory.location.application.usecase.GetLocationsByIdsUseCase;
import com.indiana.service.inventory.location.application.usecase.SaveOrUpdateLocationUseCase;
import com.indiana.service.inventory.location.application.usecase.SearchLocationUseCase;
import com.indiana.service.inventory.location.infrastructure.exception.LocationInfraException;
import com.indiana.service.inventory.product.application.exception.ProductException;
import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import com.indiana.service.inventory.product.infrastructure.exception.ProductInfraException;
import java.util.List;
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
public class LocationService {

  private final SaveOrUpdateLocationUseCase createOrUpdateLocationUseCase;
  private final GetLocationByIdUseCase getLocationByIdUseCase;
  private final GetLocationsByIdsUseCase getLocationsByIdsUseCase;
  private final SearchLocationUseCase searchLocationUseCase;

  public LocationDto saveOrUpdate(final LocationDto dto) {
    try {
      return createOrUpdateLocationUseCase.execute(dto);
    } catch (final LocationException exception) {
      throw new LocationInfraException(exception);
    }
  }

  public LocationDto getById(final Long id) {
    try {
      return getLocationByIdUseCase.execute(id);
    } catch (final LocationException exception) {
      throw new LocationInfraException(exception);
    }
  }

  public List<LocationDto> getAllByIds(final List<Long> ids) {
    try {
      return getLocationsByIdsUseCase.execute(ids);
    } catch (final LocationException exception) {
      throw new LocationInfraException(exception);
    }
  }

  public Page<LocationDto> search(final String query, final Integer page,
      final Integer size, final String sortBy, final String sortOrder) {
    try {
      if(size > 300) {
        throw new LocationException(LocationExceptionType.LOCATION_SIZE_PAGINATION_LIMIT);
      }
      final PageRequest pageRequest = getPageRequest(page, size, sortBy, sortOrder);
      return searchLocationUseCase.execute(query, pageRequest);
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
    final Sort sort = Sort.by(Sort.Direction.fromOptionalString(sortOrder).orElse(Direction.DESC),
        sortBy != null ? sortBy : ProductSortBy.ID.getValue());
    return PageRequest.of(page, size, sort);
  }
}
