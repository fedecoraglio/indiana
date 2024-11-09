package com.indiana.product.api.product.infrastructure.service;

import static com.indiana.product.api.product.application.exception.ProductException.ProductExceptionType.PRODUCT_SIZE_PAGINATION_LIMIT;

import com.indiana.product.api.product.application.sort.ProductSortBy;
import com.indiana.product.api.product.application.usecase.CreateOrUpdateProductUseCase;
import com.indiana.product.api.product.application.dto.ProductDto;
import com.indiana.product.api.product.application.exception.ProductException;
import com.indiana.product.api.product.application.usecase.GetProductByIdUseCase;
import com.indiana.product.api.product.application.usecase.SearchProductUseCase;
import com.indiana.product.api.product.infrastructure.exception.ProductInfraException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

  private final CreateOrUpdateProductUseCase createOrUpdateProductUseCase;
  private final SearchProductUseCase searchProductUseCase;
  private final GetProductByIdUseCase getProductByIdUseCase;

  public ProductDto createOrUpdateProduct(final ProductDto productDto) {
    try {
      return createOrUpdateProductUseCase.execute(productDto);
    } catch (final ProductException e) {
      log.error("Error creating product", e);
      throw new ProductInfraException(e);
    }
  }

  public Page<ProductDto> searchProduct(final String query, final Integer page,
      final Integer size, final String sortBy, final String sortOrder) {
    try {
      if(size > 300) {
        throw new ProductException(PRODUCT_SIZE_PAGINATION_LIMIT);
      }
      final PageRequest pageRequest = getPageRequest(page, size, sortBy, sortOrder);
      return searchProductUseCase.execute(query, pageRequest);
    } catch (final ProductException e) {
      log.error("Error searching product", e);
      throw new ProductInfraException(e);
    }
  }

  public Optional<ProductDto> getProduct(final Long id) {
    try {
      return Optional.ofNullable(getProductByIdUseCase.execute(id));
    } catch (final ProductException e) {
      return Optional.empty();
    }
  }

  private PageRequest getPageRequest(final Integer page, final Integer size, final String sortBy,
      final String sortOrder) {
    if (page == null || size == null) {
      return null;
    }
    final Sort sort = Sort.by(
        Sort.Direction.fromOptionalString(sortOrder).orElse(Direction.DESC),
        sortBy != null ? sortBy : ProductSortBy.ID.getValue());
    return PageRequest.of(page, size, sort);
  }
}
