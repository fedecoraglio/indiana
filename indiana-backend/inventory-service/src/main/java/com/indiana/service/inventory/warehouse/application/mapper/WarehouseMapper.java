package com.indiana.service.inventory.warehouse.application.mapper;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.domain.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

  public WarehouseDto toDto(final Warehouse dto) {
    if (dto == null) {
      return null;
    }
    return WarehouseDto.builder().id(dto.getId())
        .name(dto.getName())
        .locationId(dto.getLocationId())
        .build();
  }

  public Warehouse toDomain(final WarehouseDto dto) {
    if (dto == null) {
      return null;
    }
    final Warehouse item = new Warehouse();
    item.setId(dto.id());
    item.setName(dto.name());
    item.setLocationId(dto.locationId());
    return item;
  }
}
