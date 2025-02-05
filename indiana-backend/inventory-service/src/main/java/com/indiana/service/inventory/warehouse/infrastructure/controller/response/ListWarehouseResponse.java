package com.indiana.service.inventory.warehouse.infrastructure.controller.response;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListWarehouseResponse {

  Long totalElements;
  Integer totalPages;
  Integer pageSize;
  Integer page;
  Integer numberOfElements;
  List<WarehouseDto> content;

}
