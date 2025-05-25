package com.indiana.service.inventory.provider.application.usecase;

import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import com.indiana.service.inventory.provider.application.mapper.ProviderMapper;
import com.indiana.service.inventory.provider.domain.ProviderRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GetProviderByIdsUseCase {

  private final ProviderRepository providerRepository;
  private final ProviderMapper providerMapper;

  public List<ProviderDto> execute(final List<Long> ids) {
    return providerMapper.toDto(providerRepository.findAllByIdIn(ids));
  }
}
