package com.indiana.service.inventory.product.application.policy;

import com.indiana.service.inventory.product.application.dto.ProductDto;
import com.indiana.service.inventory.product.application.exception.ProductException;
import com.indiana.service.inventory.product.application.exception.ProductException.ProductExceptionType;
import com.indiana.service.inventory.product.domain.Product;
import com.indiana.service.inventory.product.domain.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreateOrUpdateProductPolicy {

  private final ProductRepository productRepository;

  public void check(final ProductDto productDto) throws ProductException {
    if (productDto == null) {
      throw new ProductException(ProductExceptionType.PRODUCT_CANNOT_BE_NULL);
    }
    if (productDto.getName() == null || productDto.getName().isEmpty()) {
      throw new ProductException(ProductExceptionType.PRODUCT_NAME_CANNOT_BE_NULL_OR_EMPTY);
    }
    if (productDto.getCode() == null || productDto.getCode().isEmpty()) {
      throw new ProductException(ProductExceptionType.PRODUCT_NAME_CANNOT_BE_NULL_OR_EMPTY);
    }
    if (productDto.getBarcode() == null || productDto.getBarcode().isEmpty()) {
      throw new ProductException(ProductExceptionType.PRODUCT_NAME_CANNOT_BE_NULL_OR_EMPTY);
    }
    final List<Product> products = productRepository.findByCodeIgnoreCaseOrBarcodeIgnoreCaseOrNameIgnoreCase(
        productDto.getCode(), productDto.getBarcode(), productDto.getName(), PageRequest.of(0, 1));
    if (!products.isEmpty()) {
      if (productDto.getId() == null) {
        throw new ProductException(ProductException.ProductExceptionType.PRODUCT_ALREADY_EXISTS);
      } else if(!productDto.getId().equals(products.getFirst().getId())) {
          throw new ProductException(ProductException.ProductExceptionType.PRODUCT_ALREADY_EXISTS);
      }
    }
  }
}
