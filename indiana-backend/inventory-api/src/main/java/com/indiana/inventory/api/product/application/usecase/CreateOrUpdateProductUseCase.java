package com.indiana.inventory.api.product.application.usecase;

import com.indiana.inventory.api.product.application.exception.ProductException;
import com.indiana.inventory.api.product.application.exception.ProductException.ProductExceptionType;
import com.indiana.inventory.api.product.application.policy.CreateOrUpdateProductPolicy;
import com.indiana.inventory.api.product.domain.Product;
import com.indiana.inventory.api.product.domain.ProductRepository;
import com.indiana.inventory.api.product.application.dto.ProductDto;
import com.indiana.inventory.api.product.application.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class CreateOrUpdateProductUseCase {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final CreateOrUpdateProductPolicy createOrUpdateProductPolicy;

  public ProductDto execute(final ProductDto productDto) {
    try {
      createOrUpdateProductPolicy.check(productDto);
      final Product product = productMapper.toProduct(productDto);
      final Product newProduct = productRepository.save(product);
      return productMapper.toProductDto(newProduct);
    } catch (final ProductException e) {
      log.error("Error creating product", e);
      throw e;
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
      throw new ProductException(ProductExceptionType.PRODUCT_UNEXPECTED_ERROR, e);
    }
  }
}
