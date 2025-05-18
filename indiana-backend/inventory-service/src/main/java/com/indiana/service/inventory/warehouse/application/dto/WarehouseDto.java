package com.indiana.service.inventory.warehouse.application.dto;

import lombok.Builder;

@Builder
public record WarehouseDto(Long id, String name, Long locationId) {

}
