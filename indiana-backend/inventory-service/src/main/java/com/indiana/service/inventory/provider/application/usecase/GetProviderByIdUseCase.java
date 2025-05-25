package com.indiana.service.inventory.provider.application.usecase;

import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import com.indiana.service.inventory.provider.application.mapper.ProviderMapper;
import com.indiana.service.inventory.provider.domain.ProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.indiana.service.inventory.provider.application.exception.ProviderException;
import com.indiana.service.inventory.provider.application.exception.ProviderException.ProviderExceptionType;

@AllArgsConstructor
@Component
public class GetProviderByIdUseCase {

  private final ProviderRepository providerRepository;
  private final ProviderMapper providerMapper;

  public ProviderDto execute(final Long id) {
    try {
      return providerMapper.toDto(providerRepository.findById(id).orElse(null));
    } catch (final Exception e) {
      throw new ProviderException(ProviderExceptionType.PROVIDER_UNEXPECTED_ERROR, e);
    }
  }
}
