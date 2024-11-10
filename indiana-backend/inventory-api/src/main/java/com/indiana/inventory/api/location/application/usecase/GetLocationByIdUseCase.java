package com.indiana.inventory.api.location.application.usecase;

import com.indiana.inventory.api.location.application.dto.LocationDto;
import com.indiana.inventory.api.location.application.exception.LocationException;
import com.indiana.inventory.api.location.application.exception.LocationException.LocationExceptionType;
import com.indiana.inventory.api.location.application.mapper.LocationMapper;
import com.indiana.inventory.api.location.domain.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GetLocationByIdUseCase {

  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;

  public LocationDto execute(final Long id) {
    try {
      return locationMapper.toLocationDto(locationRepository.findById(id).orElse(null));
    } catch (final Exception e) {
      throw new LocationException(LocationExceptionType.LOCATION_UNEXPECTED_ERROR, e);
    }
  }
}
