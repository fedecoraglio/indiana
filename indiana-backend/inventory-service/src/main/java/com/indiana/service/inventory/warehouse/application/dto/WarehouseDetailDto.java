package com.indiana.service.inventory.warehouse.application.dto;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WarehouseDetailDto {

  private final Long id;
  private final String name;
  private final LocationDto location;
}
