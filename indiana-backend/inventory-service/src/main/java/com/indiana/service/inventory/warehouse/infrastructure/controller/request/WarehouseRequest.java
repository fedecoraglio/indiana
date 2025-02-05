package com.indiana.service.inventory.warehouse.infrastructure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseRequest {

  @NotBlank(message = "Name is required")
  @NotNull(message = "Name is required")
  private String name;
  @NotNull(message = "Location is required")
  private Long locationId;
}
