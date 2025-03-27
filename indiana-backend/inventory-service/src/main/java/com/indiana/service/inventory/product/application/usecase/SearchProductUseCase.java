package com.indiana.service.inventory.product.application.usecase;

import com.indiana.service.inventory.product.application.exception.ProductException;
import com.indiana.service.inventory.product.application.exception.ProductException.ProductExceptionType;
import com.indiana.service.inventory.product.domain.Product;
import com.indiana.service.inventory.product.domain.ProductPageableRepository;
import com.indiana.service.inventory.product.application.dto.ProductDto;
import com.indiana.service.inventory.product.application.mapper.ProductMapper;
import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SearchProductUseCase {

  private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 20,
      Sort.by(ProductSortBy.ID.getValue()).descending());
  private final ProductPageableRepository productPageableRepository;
  private final ProductMapper productMapper;

  public Page<ProductDto> execute(final String query, final PageRequest pageRequest) {
    try {
      final PageRequest pageable = pageRequest != null ? pageRequest : PAGE_REQUEST;
      Page<Product> products = null;
      if (query == null || query.isEmpty()) {
        products = productPageableRepository.findAll(pageable);
      } else {
        products = productPageableRepository.findByCodeStartingWithOrBarcodeStartingWithOrNameStartingWithOrderById(
            query, query, query, pageable);
      }
      return products.map(productMapper::toProductDto);
    } catch (final ProductException e) {
      log.error("Error creating product", e);
      throw e;
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
      throw new ProductException(ProductExceptionType.PRODUCT_UNEXPECTED_ERROR, e);
    }
  }
}
