package com.indiana.service.inventory.location.infrastructure.controller;

import com.indiana.service.inventory.location.application.dto.LocationDto;
import com.indiana.service.inventory.location.application.sort.LocationSortBy;
import com.indiana.service.inventory.location.infrastructure.controller.convert.LocationRequestConverter;
import com.indiana.service.inventory.location.infrastructure.controller.request.LocationRequest;
import com.indiana.service.inventory.location.infrastructure.controller.response.ListLocationResponse;
import com.indiana.service.inventory.location.infrastructure.service.LocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/locations")
public class LocationController {

  private final LocationService locationService;
  private final LocationRequestConverter locationRequestConverter;

  @PostMapping()
  public ResponseEntity<LocationDto> save(@Valid @RequestBody final LocationRequest request) {
    log.info("Creating location: {}", request);
    final LocationDto dto = locationRequestConverter.convert(request);
    final LocationDto result = locationService.saveOrUpdate(dto);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<LocationDto> update(@PathVariable(name = "id") Long id,
      @Valid @RequestBody final LocationRequest request) {
    log.info("Updating location: {}", request);
    final LocationDto dto = locationRequestConverter.convert(request);
    final LocationDto dtoToUpdate = new LocationDto(id, dto.getName(), dto.getAddress());
    final LocationDto result = locationService.saveOrUpdate(dtoToUpdate);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<LocationDto> getById(@PathVariable(name = "id") final Long id) {
    log.info("Getting location: {}", id);
    final LocationDto dto = locationService.getById(id);
    if (dto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dto);
  }

  @GetMapping()
  public ResponseEntity<ListLocationResponse> search(
      @RequestParam(value = "query", required = false) final String query,
      @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "15") final Integer size,
      @RequestParam(value = "sortBy", required = false, defaultValue = "id") final String sortBy,
      @RequestParam(value = "sortOrder", required = false) final String sortOrder) {
    log.info("Searching product: {}", query);
    final Page<LocationDto> result = locationService.search(query, page, size,
        LocationSortBy.fromValue(sortBy).getValue(), sortOrder);
    final ListLocationResponse response = ListLocationResponse.builder()
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
