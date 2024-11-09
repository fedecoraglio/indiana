package com.indiana.product.api.product.application.sort;

import lombok.Getter;

@Getter
public enum ProductSortBy {
  ID("id"),
  CODE("code"),
  BARCODE("barcode"),
  NAME("name");

  private final String value;

  ProductSortBy(final String value) {
    this.value = value;
  }

  public static ProductSortBy fromValue(final String value) {
    for (final ProductSortBy sortBy : ProductSortBy.values()) {
      if (sortBy.getValue().equalsIgnoreCase(value)) {
        return sortBy;
      }
    }
    return ID;
  }
}
