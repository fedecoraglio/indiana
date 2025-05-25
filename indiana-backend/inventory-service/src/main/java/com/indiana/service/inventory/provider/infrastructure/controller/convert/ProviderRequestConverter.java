package com.indiana.service.inventory.provider.infrastructure.controller.convert;

import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import com.indiana.service.inventory.provider.infrastructure.controller.request.ProviderRequest;
import org.springframework.stereotype.Component;

@Component
public class ProviderRequestConverter {

  public ProviderDto convert(final ProviderRequest request) {
    return ProviderDto.builder()
        .name(request.getName())
        .address(request.getAddress())
        .build();
  }
}
