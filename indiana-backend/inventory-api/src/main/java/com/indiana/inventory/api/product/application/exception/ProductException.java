package com.indiana.inventory.api.product.application.exception;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {

  private ProductExceptionType type;

  public ProductException(final ProductExceptionType type) {
    super(type.getMessage());
    this.type = type;
  }

  public ProductException(final ProductExceptionType type, final String message) {
    super(message != null ? message : type.getMessage());
    this.type = type;
  }

  public ProductException(final ProductExceptionType type, final Throwable cause) {
    super(type.getMessage(), cause);
  }

  @Getter
  public enum ProductExceptionType {
    PRODUCT_CANNOT_BE_NULL("product-1000", "Product cannot be null"),
    PRODUCT_NAME_CANNOT_BE_NULL_OR_EMPTY("product-1001", "Product name cannot be null or empty"),
    PRODUCT_UNEXPECTED_ERROR("product-1002", "Unexpected error creating product"),
    PRODUCT_ALREADY_EXISTS("product-1003", "Product already exists"),
    PRODUCT_SIZE_PAGINATION_LIMIT("product-1004", "Pagination limit exceed");

    private final String code;
    private final String message;

    ProductExceptionType(final String code, final String message) {
      this.code = code;
      this.message = message;
    }
  }
}
