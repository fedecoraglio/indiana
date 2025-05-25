package com.indiana.service.inventory.provider.application.usecase;

import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import com.indiana.service.inventory.provider.application.exception.ProviderException;
import com.indiana.service.inventory.provider.application.exception.ProviderException.ProviderExceptionType;
import com.indiana.service.inventory.provider.application.mapper.ProviderMapper;
import com.indiana.service.inventory.provider.domain.Provider;
import com.indiana.service.inventory.provider.domain.ProviderPageableRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SearchProviderUseCase {

  private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 20,
      Sort.by(ProductSortBy.ID.getValue()).descending());
  private final ProviderPageableRepository providerPageableRepository;
  private final ProviderMapper providerMapper;

  public Page<ProviderDto> execute(final String query, final PageRequest pageRequest) {
    try {
      final PageRequest pageable = pageRequest != null ? pageRequest : PAGE_REQUEST;
      Page<Provider> page = null;
      if (query == null || query.isEmpty()) {
        page = providerPageableRepository.findAll(pageable);
      } else {
        page = providerPageableRepository.findByNameIgnoreCaseContaining(query, pageable);
      }
      return page.map(providerMapper::toDto);
    } catch (final ProviderException e) {
      log.error("Error getting provider", e);
      throw e;
    } catch (final Exception e) {
      log.error(e.getMessage(), e);
      throw new ProviderException(ProviderExceptionType.PROVIDER_UNEXPECTED_ERROR, e);
    }
  }
}
