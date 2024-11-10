package com.indiana.inventory.api.product.infrastructure.controller;

import com.indiana.inventory.api.product.infrastructure.service.ProductService;
import com.indiana.inventory.api.product.application.dto.ProductDto;
import com.indiana.inventory.api.product.application.sort.ProductSortBy;
import com.indiana.inventory.api.product.infrastructure.controller.convert.ProductRequestConverter;
import com.indiana.inventory.api.product.infrastructure.controller.response.ListProductResponse;
import com.indiana.inventory.api.product.infrastructure.controller.request.ProductRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
@RequestMapping("/v1/products")
public class ProductController {

  private final ProductService productService;
  private final ProductRequestConverter productRequestConverter;

  @PostMapping()
  public ResponseEntity<ProductDto> createProduct(
      @Valid @RequestBody final ProductRequest request) {
    log.info("Creating product: {}", request);
    final ProductDto productDto = productRequestConverter.convert(request);
    final ProductDto result = productService.createOrUpdateProduct(productDto);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(
      @PathVariable(name = "id") Long id,
      @Valid @RequestBody final ProductRequest request) {
    log.info("Updating product: {}", request);
    final ProductDto productDto = productRequestConverter.convert(request);
    final ProductDto updateProductDto = new ProductDto(id, productDto.getCode(),
        productDto.getBarcode(), productDto.getName(), productDto.getDescription(),
        productDto.getPackedWeight(), productDto.getPackedWidth(), productDto.getPackedDepth(),
        productDto.getRefrigerated());
    final ProductDto result = productService.createOrUpdateProduct(updateProductDto);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable(name = "id") Long id) {
    log.info("Getting product: {}", id);
    return productService.getProduct(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping()
  public ResponseEntity<ListProductResponse> searchProduct(@Param("query") final String query,
      @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
      @RequestParam(value = "pageSize", required = false, defaultValue = "15") final Integer size,
      @RequestParam(value = "sortBy", required = false, defaultValue = "id") final String sortBy,
      @RequestParam(value = "sortOrder", required = false) final String sortOrder) {
    log.info("Searching product: {}", query);
    final Page<ProductDto> result = productService.searchProduct(query, page, size,
        ProductSortBy.fromValue(sortBy).getValue(), sortOrder);
    final ListProductResponse response = ListProductResponse.builder()
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
