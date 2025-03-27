package com.indiana.service.inventory.location.infrastructure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationRequest {

  @NotBlank(message = "Name is required")
  @NotNull(message = "Name is required")
  private String name;
  @NotBlank(message = "Address is required")
  @NotNull(message = "Address is required")
  private String address;
}
