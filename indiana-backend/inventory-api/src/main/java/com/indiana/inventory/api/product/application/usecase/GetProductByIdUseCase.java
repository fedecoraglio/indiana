package com.indiana.inventory.api.product.application.usecase;

import com.indiana.inventory.api.product.application.exception.ProductException;
import com.indiana.inventory.api.product.application.exception.ProductException.ProductExceptionType;
import com.indiana.inventory.api.product.domain.ProductRepository;
import com.indiana.inventory.api.product.application.dto.ProductDto;
import com.indiana.inventory.api.product.application.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GetProductByIdUseCase {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductDto execute(final Long id) {
    try {
      return productMapper.toProductDto(productRepository.findById(id).orElse(null));
    } catch (final Exception e) {
      throw new ProductException(ProductExceptionType.PRODUCT_UNEXPECTED_ERROR, e);
    }
  }
}
