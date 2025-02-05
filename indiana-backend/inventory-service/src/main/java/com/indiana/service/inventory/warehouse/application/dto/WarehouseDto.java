package com.indiana.service.inventory.warehouse.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class WarehouseDto {

  private final Long id;
  private final String name;
  private final Long locationId;
}
