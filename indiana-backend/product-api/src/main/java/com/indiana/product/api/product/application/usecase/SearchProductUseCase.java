package com.indiana.product.api.product.application.usecase;

import static com.indiana.product.api.product.application.exception.ProductException.ProductExceptionType.PRODUCT_UNEXPECTED_ERROR;

import com.indiana.product.api.product.application.dto.ProductDto;
import com.indiana.product.api.product.application.exception.ProductException;
import com.indiana.product.api.product.application.mapper.ProductMapper;
import com.indiana.product.api.product.application.sort.ProductSortBy;
import com.indiana.product.api.product.domain.ProductPageableRepository;
import com.indiana.product.api.product.domain.Product;
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
      throw new ProductException(PRODUCT_UNEXPECTED_ERROR, e);
    }
  }
}
