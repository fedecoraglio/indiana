package com.indiana.service.inventory.location.application.usecase;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.application.exception.LocationException;
import com.indiana.service.inventory.location.application.exception.LocationException.LocationExceptionType;
import com.indiana.service.inventory.location.application.mapper.LocationMapper;
import com.indiana.service.inventory.location.domain.LocationRepository;
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
