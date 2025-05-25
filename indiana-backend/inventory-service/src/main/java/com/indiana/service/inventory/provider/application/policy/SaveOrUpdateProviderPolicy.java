package com.indiana.service.inventory.provider.application.policy;

import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.indiana.service.inventory.provider.application.exception.ProviderException;
import com.indiana.service.inventory.provider.application.exception.ProviderException.ProviderExceptionType;

@AllArgsConstructor
@Component
public class SaveOrUpdateProviderPolicy {

  public void check(final ProviderDto dto) {
    if (dto == null) {
      throw new ProviderException(ProviderExceptionType.PROVIDER_CANNOT_BE_NULL);
    }
    if ("".equals(dto.name())) {
      throw new ProviderException(ProviderExceptionType.PROVIDER_NAME_CANNOT_BE_NULL);
    }
  }
}
