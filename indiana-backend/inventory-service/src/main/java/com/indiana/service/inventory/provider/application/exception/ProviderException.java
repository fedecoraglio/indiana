package com.indiana.service.inventory.provider.application.exception;

import lombok.Getter;

@Getter
public class ProviderException extends RuntimeException {

  private ProviderExceptionType type;

  public ProviderException(final ProviderExceptionType type) {
    super(type.getMessage());
    this.type = type;
  }

  public ProviderException(final ProviderExceptionType type, final String message) {
    super(message != null ? message : type.getMessage());
    this.type = type;
  }

  public ProviderException(final ProviderExceptionType type, final Throwable cause) {
    super(type.getMessage(), cause);
  }

  @Getter
  public enum ProviderExceptionType {
    PROVIDER_CANNOT_BE_NULL("provider-1000", "Provider cannot be null"),
    PROVIDER_UNEXPECTED_ERROR("provider-1001", "Unexpected error on provider"),
    PROVIDER_SIZE_PAGINATION_LIMIT("provider-1002", "Pagination limit exceed"),
    PROVIDER_SAVING_ERROR("provider-1003", "Error saving provider"),
    PROVIDER_NAME_CANNOT_BE_NULL("provider-1004", "Name is required");

    private final String code;
    private final String message;

    ProviderExceptionType(final String code, final String message) {
      this.code = code;
      this.message = message;
    }
  }
}
