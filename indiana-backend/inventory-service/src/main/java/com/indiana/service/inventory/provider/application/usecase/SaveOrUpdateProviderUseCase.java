package com.indiana.service.inventory.provider.application.usecase;

import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import com.indiana.service.inventory.provider.application.exception.ProviderException;
import com.indiana.service.inventory.provider.application.exception.ProviderException.ProviderExceptionType;
import com.indiana.service.inventory.provider.application.mapper.ProviderMapper;
import com.indiana.service.inventory.provider.application.policy.SaveOrUpdateProviderPolicy;
import com.indiana.service.inventory.provider.domain.Provider;
import com.indiana.service.inventory.provider.domain.ProviderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Component
public class SaveOrUpdateProviderUseCase {

  private final ProviderRepository providerRepository;
  private final ProviderMapper providerMapper;
  private final SaveOrUpdateProviderPolicy saveOrUpdateProviderPolicy;

  @Transactional
  public ProviderDto execute(final ProviderDto dto) {
    try {
      log.info("Starting updating provider");
      saveOrUpdateProviderPolicy.check(dto);
      final Provider entity = providerMapper.toEntity(dto);
      final Provider saved = providerRepository.save(entity);
      log.info("Ending updating provider. Id: {}", saved.getId());
      return providerMapper.toDto(saved);
    } catch (final ProviderException e) {
      log.error("Error updating provider", e);
      throw e;
    } catch (final Exception e) {
      throw new ProviderException(ProviderExceptionType.PROVIDER_SAVING_ERROR, e);
    }
  }
}
