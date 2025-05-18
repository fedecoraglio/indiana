package com.indiana.service.inventory.location.application.mapper;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.domain.Location;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

  public LocationDto toDto(final Location location) {
    if (location == null) {
      return null;
    }
    return LocationDto.builder().id(location.getId())
        .name(location.getName())
        .address(location.getAddress())
        .build();
  }

  public List<LocationDto> toDto(final List<Location> list) {
    if (list == null) {
      return null;
    }
    return list.stream().map(this::toDto).toList();
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
