package com.indiana.service.inventory.warehouse.application.sort;

import lombok.Getter;

@Getter
public enum WarehouseSortBy {
  ID("id"),
  NAME("name"),
  LOCATION_ID("loc_id");

  private final String value;

  WarehouseSortBy(final String value) {
    this.value = value;
  }

  public static WarehouseSortBy fromValue(final String value) {
    for (final WarehouseSortBy sortBy : WarehouseSortBy.values()) {
      if (sortBy.getValue().equalsIgnoreCase(value)) {
        return sortBy;
      }
    }
    return ID;
  }
}
