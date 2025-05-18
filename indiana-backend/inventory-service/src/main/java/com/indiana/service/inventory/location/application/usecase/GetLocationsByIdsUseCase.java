package com.indiana.service.inventory.location.application.usecase;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.application.mapper.LocationMapper;
import com.indiana.service.inventory.location.domain.LocationRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GetLocationsByIdsUseCase {

  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;

  public List<LocationDto> execute(final List<Long> ids) {
    return locationMapper.toDto(locationRepository.findAllByIdIn(ids));
  }
}
