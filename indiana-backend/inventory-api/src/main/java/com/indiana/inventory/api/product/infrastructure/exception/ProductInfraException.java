package com.indiana.inventory.api.product.infrastructure.exception;

import com.indiana.inventory.api.product.application.exception.ProductException;
import lombok.Getter;

@Getter
public class ProductInfraException extends RuntimeException {

  private final ProductException productException;

  public ProductInfraException(final ProductException productException) {
    super(productException.getMessage(), productException);
    this.productException = productException;
  }
}
