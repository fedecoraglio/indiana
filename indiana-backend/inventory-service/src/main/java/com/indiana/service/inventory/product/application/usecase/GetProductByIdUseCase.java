package com.indiana.service.inventory.product.application.usecase;

import com.indiana.service.inventory.product.application.exception.ProductException;
import com.indiana.service.inventory.product.application.exception.ProductException.ProductExceptionType;
import com.indiana.service.inventory.product.domain.ProductRepository;
import com.indiana.service.inventory.product.application.dto.ProductDto;
import com.indiana.service.inventory.product.application.mapper.ProductMapper;
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
