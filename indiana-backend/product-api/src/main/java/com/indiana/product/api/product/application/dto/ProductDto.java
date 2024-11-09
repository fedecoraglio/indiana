package com.indiana.product.api.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ProductDto {

  private final Long id;
  private final String code;
  private final String barcode;
  private final String name;
  private final String description;
  private final Double packedWeight;
  private final Double packedWidth;
  private final Double packedDepth;
  private final Boolean refrigerated;
}
