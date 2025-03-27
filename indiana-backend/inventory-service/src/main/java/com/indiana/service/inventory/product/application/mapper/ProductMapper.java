package com.indiana.service.inventory.product.application.mapper;

import com.indiana.service.inventory.product.application.dto.ProductDto;
import com.indiana.service.inventory.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductDto toProductDto(final Product product) {
    if (product == null) {
      return null;
    }
    return ProductDto.builder().id(product.getId()).code(product.getCode())
        .barcode(product.getBarcode()).name(product.getName()).description(product.getDescription())
        .packedWeight(product.getPackedWeight()).packedWidth(product.getPackedWidth())
        .packedDepth(product.getPackedDepth()).refrigerated(product.getRefrigerated()).build();
  }

  public Product toProduct(final ProductDto productDto) {
    if (productDto == null) {
      return null;
    }
    final Product product = new Product();
    product.setId(productDto.getId());
    product.setName(productDto.getName());
    product.setDescription(productDto.getDescription());
    product.setBarcode(productDto.getBarcode());
    product.setCode(productDto.getCode());
    product.setPackedWeight(productDto.getPackedWeight());
    product.setPackedWidth(productDto.getPackedWidth());
    product.setPackedDepth(productDto.getPackedDepth());
    product.setRefrigerated(productDto.getRefrigerated());
    return product;
  }
}
