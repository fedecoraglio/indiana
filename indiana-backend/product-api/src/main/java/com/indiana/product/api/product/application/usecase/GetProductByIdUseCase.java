package com.indiana.product.api.product.application.usecase;

import static com.indiana.product.api.product.application.exception.ProductException.ProductExceptionType.PRODUCT_UNEXPECTED_ERROR;

import com.indiana.product.api.product.application.dto.ProductDto;
import com.indiana.product.api.product.application.exception.ProductException;
import com.indiana.product.api.product.application.mapper.ProductMapper;
import com.indiana.product.api.product.domain.ProductRepository;
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
      throw new ProductException(PRODUCT_UNEXPECTED_ERROR, e);
    }
  }
}
