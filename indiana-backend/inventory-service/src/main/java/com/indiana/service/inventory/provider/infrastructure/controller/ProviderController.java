package com.indiana.service.inventory.provider.infrastructure.controller;

import com.indiana.service.inventory.common.response.ListResponse;
import com.indiana.service.inventory.provider.application.dto.ProviderDto;
import com.indiana.service.inventory.provider.application.sort.ProviderSortBy;
import com.indiana.service.inventory.provider.infrastructure.controller.convert.ProviderRequestConverter;
import com.indiana.service.inventory.provider.infrastructure.controller.request.ProviderRequest;
import com.indiana.service.inventory.provider.infrastructure.service.ProviderService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/providers")
public class ProviderController {

  private final ProviderService providerService;
  private final ProviderRequestConverter providerRequestConverter;

  @PostMapping()
  public ResponseEntity<ProviderDto> save(@Valid @RequestBody final ProviderRequest request) {
    log.info("Creating provider: {}", request);
    final ProviderDto dto = providerRequestConverter.convert(request);
    final ProviderDto result = providerService.saveOrUpdate(dto);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProviderDto> update(@PathVariable(name = "id") final Long id,
      @Valid @RequestBody final ProviderRequest request) {
    log.info("Updating provider: {}", request);
    final ProviderDto dto = providerRequestConverter.convert(request);
    final ProviderDto dtoToUpdate = new ProviderDto(id, dto.name(), dto.address());
    final ProviderDto result = providerService.saveOrUpdate(dtoToUpdate);
    return ResponseEntity.ok(result);
  }

  @PostMapping("by-ids")
  public ResponseEntity<List<ProviderDto>> getByIds(@RequestBody() final List<Long> ids) {
    log.info("Getting providers by ids: {}", ids);
    return ResponseEntity.ok(providerService.getAllByIds(ids));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProviderDto> getById(@PathVariable(name = "id") final Long id) {
    log.info("Getting provider: {}", id);
    final ProviderDto dto = providerService.getById(id);
    if (dto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dto);
  }

  @GetMapping()
  public ResponseEntity<ListResponse<ProviderDto>> search(
      @RequestParam(value = "query", required = false) final String query,
      @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "15") final Integer size,
      @RequestParam(value = "sortBy", required = false, defaultValue = "id") final String sortBy,
      @RequestParam(value = "sortOrder", required = false) final String sortOrder) {
    log.info("Searching product: {}", query);
    final Page<ProviderDto> result = providerService.search(query, page, size,
        ProviderSortBy.fromValue(sortBy).getValue(), sortOrder);
    final ListResponse<ProviderDto> response =  ListResponse.toListResponse(result);
    return ResponseEntity.ok(response);
  }
}
