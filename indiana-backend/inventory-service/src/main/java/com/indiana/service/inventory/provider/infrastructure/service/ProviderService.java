package com.indiana.service.inventory.provider.infrastructure.service;

import com.indiana.service.inventory.product.application.exception.ProductException;
import com.indiana.service.inventory.product.application.sort.ProductSortBy;
import com.indiana.service.inventory.product.infrastructure.exception.ProductInfraException;
import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import com.indiana.service.inventory.provider.application.exception.ProviderException;
import com.indiana.service.inventory.provider.application.exception.ProviderException.ProviderExceptionType;
import com.indiana.service.inventory.provider.application.usecase.GetProviderByIdUseCase;
import com.indiana.service.inventory.provider.application.usecase.GetProviderByIdsUseCase;
import com.indiana.service.inventory.provider.application.usecase.SaveOrUpdateProviderUseCase;
import com.indiana.service.inventory.provider.application.usecase.SearchProviderUseCase;
import com.indiana.service.inventory.provider.infrastructure.exception.ProviderInfraException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ProviderService {

  private final SaveOrUpdateProviderUseCase saveOrUpdateProviderUseCase;
  private final GetProviderByIdUseCase getProviderByIdUseCase;
  private final GetProviderByIdsUseCase getProviderByIdsUseCase;
  private final SearchProviderUseCase searchProviderUseCase;

  public ProviderDto saveOrUpdate(final ProviderDto dto) {
    try {
      return saveOrUpdateProviderUseCase.execute(dto);
    } catch (final ProviderException exception) {
      throw new ProviderInfraException(exception);
    }
  }

  public ProviderDto getById(final Long id) {
    try {
      return getProviderByIdUseCase.execute(id);
    } catch (final ProviderException exception) {
      throw new ProviderInfraException(exception);
    }
  }

  public List<ProviderDto> getAllByIds(final List<Long> ids) {
    try {
      return getProviderByIdsUseCase.execute(ids);
    } catch (final ProviderException exception) {
      throw new ProviderInfraException(exception);
    }
  }

  public Page<ProviderDto> search(final String query, final Integer page,
      final Integer size, final String sortBy, final String sortOrder) {
    try {
      if(size > 300) {
        throw new ProviderException(ProviderExceptionType.PROVIDER_SIZE_PAGINATION_LIMIT);
      }
      final PageRequest pageRequest = getPageRequest(page, size, sortBy, sortOrder);
      return searchProviderUseCase.execute(query, pageRequest);
    } catch (final ProductException e) {
      log.error("Error searching product", e);
      throw new ProductInfraException(e);
    }
  }

  //TODO: move this method to a new class.
  private PageRequest getPageRequest(final Integer page, final Integer size, final String sortBy,
      final String sortOrder) {
    if (page == null || size == null) {
      return null;
    }
    final Sort sort = Sort.by(Direction.fromOptionalString(sortOrder).orElse(Direction.DESC),
        sortBy != null ? sortBy : ProductSortBy.ID.getValue());
    return PageRequest.of(page, size, sort);
  }
}
