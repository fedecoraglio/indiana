package com.indiana.inventory.api.product.infrastructure.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

  @NotBlank(message = "Code is required")
  @NotNull(message = "Code is required")
  private String code;
  @NotBlank(message = "Barcode is required")
  @NotNull(message = "Barcode is required")
  private String barcode;
  @NotBlank(message = "Name is required")
  @NotNull(message = "Name is required")
  private String name;
  private String description;
  private Double packedWeight;
  private Double packedWidth;
  private Double packedDepth;
  private Boolean refrigerated;
}
