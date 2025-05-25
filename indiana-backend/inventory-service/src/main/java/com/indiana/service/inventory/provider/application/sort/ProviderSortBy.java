package com.indiana.service.inventory.provider.application.sort;

import lombok.Getter;

@Getter
public enum ProviderSortBy {
  ID("id"),
  NAME("name"),
  ADDRESS("address");

  private final String value;

  ProviderSortBy(final String value) {
    this.value = value;
  }

  public static ProviderSortBy fromValue(final String value) {
    for (final ProviderSortBy sortBy : ProviderSortBy.values()) {
      if (sortBy.getValue().equalsIgnoreCase(value)) {
        return sortBy;
      }
    }
    return ID;
  }
}
