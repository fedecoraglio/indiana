package com.indiana.service.inventory.warehouse.application.usecase;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException;
import com.indiana.service.inventory.warehouse.application.exception.WarehouseException.WarehouseExceptionType;
import com.indiana.service.inventory.warehouse.application.mapper.WarehouseMapper;
import com.indiana.service.inventory.warehouse.application.policy.SaveOrUpdateWarehousePolicy;
import com.indiana.service.inventory.warehouse.domain.Warehouse;
import com.indiana.service.inventory.warehouse.domain.WarehouseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Component
public class SaveOrUpdateWarehouseUseCase {

  private final WarehouseRepository warehouseRepository;
  private final WarehouseMapper warehouseMapper;
  private final SaveOrUpdateWarehousePolicy saveOrUpdateWarehousePolicy;

  @Transactional
  public WarehouseDto execute(final WarehouseDto dto) {
    try {
      log.info("Starting updating warehouse");
      saveOrUpdateWarehousePolicy.check(dto);
      final Warehouse locationSaved = warehouseRepository.save(warehouseMapper.toLocation(dto));
      log.info("Ending updating warehouse. Id: {}", locationSaved.getId());
      return warehouseMapper.toLocationDto(locationSaved);
    } catch (final WarehouseException e) {
      log.error("Error updating location", e);
      throw e;
    } catch (final Exception e) {
      throw new WarehouseException(WarehouseExceptionType.WAREHOUSE_SAVING_ERROR, e);
    }
  }
}
