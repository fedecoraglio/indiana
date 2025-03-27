package com.indiana.service.inventory.location.infrastructure.controller.convert;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.infrastructure.controller.request.LocationRequest;
import org.springframework.stereotype.Component;

@Component
public class LocationRequestConverter {

  public LocationDto convert(final LocationRequest request) {
    return LocationDto.builder()
        .name(request.getName())
        .address(request.getAddress())
        .build();
  }
}
