package com.indiana.service.inventory.warehouse.application.policy;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException.WarehouseExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SaveOrUpdateWarehousePolicy {

  public void check(final WarehouseDto dto) {
    if (dto == null) {
      throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_CANNOT_BE_NULL);
    }
    if ("".equals(dto.name())) {
      throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_NAME_CANNOT_BE_NULL);
    }
    if(dto.locationId() == null) {
      throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_LOCATION_ID_CANNOT_BE_NULL);
    }
  }
}
