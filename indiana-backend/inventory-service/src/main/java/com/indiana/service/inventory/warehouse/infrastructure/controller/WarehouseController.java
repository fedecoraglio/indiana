package com.indiana.service.inventory.warehouse.infrastructure.controller;

import com.indiana.service.inventory.warehouse.application.dto.WarehouseDetailDto;
import com.indiana.service.inventory.warehouse.application.dto.WarehouseDto;
import com.indiana.service.inventory.warehouse.application.sort.WarehouseSortBy;
import com.indiana.service.inventory.warehouse.infrastructure.controller.convert.WarehouseRequestConverter;
import com.indiana.service.inventory.warehouse.infrastructure.controller.request.WarehouseRequest;
import com.indiana.service.inventory.warehouse.infrastructure.controller.response.ListWarehouseResponse;
import com.indiana.service.inventory.warehouse.infrastructure.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

  private final WarehouseService warehouseService;
  private final WarehouseRequestConverter warehouseRequestConverter;

  @PostMapping()
  public ResponseEntity<WarehouseDto> save(@Valid @RequestBody final WarehouseRequest request) {
    log.info("Creating warehouse: {}", request);
    final WarehouseDto dto = warehouseRequestConverter.convert(request);
    final WarehouseDto result = warehouseService.saveOrUpdate(dto);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<WarehouseDto> update(@PathVariable(name = "id") Long id,
      @Valid @RequestBody final WarehouseRequest request) {
    log.info("Updating warehouse: {}", request);
    final WarehouseDto dto = warehouseRequestConverter.convert(request);
    final WarehouseDto dtoToUpdate = new WarehouseDto(id, dto.name(), dto.locationId());
    final WarehouseDto result = warehouseService.saveOrUpdate(dtoToUpdate);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<WarehouseDetailDto> getById(@PathVariable(name = "id") final Long id) {
    log.info("Getting warehouse: {}", id);
    final WarehouseDetailDto dto = warehouseService.getById(id);
    if (dto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dto);
  }

  @GetMapping()
  public ResponseEntity<ListWarehouseResponse> search(
      @RequestParam(value = "query", required = false) final String query,
      @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "15") final Integer size,
      @RequestParam(value = "sortBy", required = false, defaultValue = "id") final String sortBy,
      @RequestParam(value = "sortOrder", required = false) final String sortOrder) {
    log.info("Searching warehouses: {}", query);
    final Page<WarehouseDetailDto> result = warehouseService.search(query, page, size,
        WarehouseSortBy.fromValue(sortBy).getValue(), sortOrder);
    final ListWarehouseResponse response = ListWarehouseResponse.builder()
        .totalElements(result.getTotalElements())
        .totalPages(result.getTotalPages())
        .pageSize(result.getSize())
        .page(result.getNumber())
        .numberOfElements(result.getNumberOfElements())
        .content(result.getContent())
        .build();
    return ResponseEntity.ok(response);
  }
}
