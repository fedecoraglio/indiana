package com.indiana.service.inventory.location.application.usecase;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.application.exception.LocationException;
import com.indiana.service.inventory.location.application.exception.LocationException.LocationExceptionType;
import com.indiana.service.inventory.location.application.mapper.LocationMapper;
import com.indiana.service.inventory.location.domain.Location;
import com.indiana.service.inventory.location.domain.LocationPageableRepository;
import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SearchLocationUseCase {

  private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 20,
      Sort.by(ProductSortBy.ID.getValue()).descending());
  private final LocationPageableRepository locationPageableRepository;
  private final LocationMapper locationMapper;

  public Page<LocationDto> execute(final String query, final PageRequest pageRequest) {
    try {
      final PageRequest pageable = pageRequest != null ? pageRequest : PAGE_REQUEST;
      Page<Location> page = null;
      if (query == null || query.isEmpty()) {
        page = locationPageableRepository.findAll(pageable);
      } else {
        page = locationPageableRepository.findByNameIgnoreCaseContaining(query, pageable);
      }
      return page.map(locationMapper::toLocationDto);
    } catch (final LocationException e) {
      log.error("Error getting location", e);
      throw e;
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
      throw new LocationException(LocationExceptionType.LOCATION_UNEXPECTED_ERROR, e);
    }
  }
}
