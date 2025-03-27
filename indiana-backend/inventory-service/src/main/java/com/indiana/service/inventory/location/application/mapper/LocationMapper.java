package com.indiana.service.inventory.location.application.mapper;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.domain.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

  public LocationDto toLocationDto(final Location location) {
    if (location == null) {
      return null;
    }
    return LocationDto.builder().id(location.getId())
        .name(location.getName())
        .address(location.getAddress())
        .build();
  }

  public Location toLocation(final LocationDto locationDto) {
    if (locationDto == null) {
      return null;
    }
    final Location location = new Location();
    location.setId(locationDto.getId());
    location.setName(locationDto.getName());
    location.setAddress(locationDto.getAddress());
    return location;
  }
}
