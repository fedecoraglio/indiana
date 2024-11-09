package com.indiana.product.api.product.infrastructure.controller.convert;

import com.indiana.product.api.product.application.dto.ProductDto;
import com.indiana.product.api.product.infrastructure.controller.request.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestConverter {

  public ProductDto convert(final ProductRequest productRequest) {
    return ProductDto.builder()
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .barcode(productRequest.getBarcode())
        .code(productRequest.getCode())
        .packedDepth(productRequest.getPackedDepth())
        .packedWeight(productRequest.getPackedWeight())
        .packedWidth(productRequest.getPackedWidth())
        .build();
  }
}
