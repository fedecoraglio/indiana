package com.indiana.service.inventory.warehouse.infrastructure.controller.convert;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.infrastructure.controller.request.WarehouseRequest;
import org.springframework.stereotype.Component;

@Component
public class WarehouseRequestConverter {

  public WarehouseDto convert(final WarehouseRequest request) {
    return WarehouseDto.builder()
        .name(request.getName())
        .locationId(request.getLocationId())
        .build();
  }
}
