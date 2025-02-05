package com.indiana.service.inventory.warehouse.application.mapper;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.domain.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

  public WarehouseDto toLocationDto(final Warehouse location) {
    if (location == null) {
      return null;
    }
    return WarehouseDto.builder().id(location.getId())
        .name(location.getName())
        .locationId(location.getLocationId())
        .build();
  }

  public Warehouse toLocation(final WarehouseDto locationDto) {
    if (locationDto == null) {
      return null;
    }
    final Warehouse location = new Warehouse();
    location.setId(locationDto.getId());
    location.setName(locationDto.getName());
    location.setLocationId(locationDto.getLocationId());
    return location;
  }
}
