package com.indiana.service.inventory.location.application.sort;

import lombok.Getter;

@Getter
public enum LocationSortBy {
  ID("id"),
  NAME("name"),
  ADDRESS("address");

  private final String value;

  LocationSortBy(final String value) {
    this.value = value;
  }

  public static LocationSortBy fromValue(final String value) {
    for (final LocationSortBy sortBy : LocationSortBy.values()) {
      if (sortBy.getValue().equalsIgnoreCase(value)) {
        return sortBy;
      }
    }
    return ID;
  }
}
