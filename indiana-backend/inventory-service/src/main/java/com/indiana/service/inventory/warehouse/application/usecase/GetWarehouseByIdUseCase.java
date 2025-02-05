package com.indiana.service.inventory.warehouse.application.usecase;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException.WarehouseExceptionType;
import com.indiana.service.inventory.warehouse.application.mapper.WarehouseMapper;
import com.indiana.service.inventory.warehouse.domain.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GetWarehouseByIdUseCase {

  private final WarehouseRepository warehouseRepository;
  private final WarehouseMapper warehouseMapperMapper;

  public WarehouseDto execute(final Long id) {
    try {
      return warehouseMapperMapper.toLocationDto(warehouseRepository.findById(id).orElse(null));
    } catch (final Exception e) {
      throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_UNEXPECTED_ERROR, e);
    }
  }
}
