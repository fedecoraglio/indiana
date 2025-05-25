package com.indiana.service.inventory.provider.infrastructure.exception;

import com.indiana.service.inventory.provider.application.exception.ProviderException;
import lombok.Getter;

@Getter
public class ProviderInfraException extends RuntimeException {

  private final ProviderException providerException;

  public ProviderInfraException(final ProviderException providerException) {
    super(providerException.getMessage(), providerException);
    this.providerException = providerException;
  }
}
