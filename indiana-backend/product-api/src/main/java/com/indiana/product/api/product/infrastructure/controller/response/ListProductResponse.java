package com.indiana.product.api.product.infrastructure.controller.response;

import com.indiana.product.api.product.application.dto.ProductDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListProductResponse {

  Long totalElements;
  Integer totalPages;
  Integer pageSize;
  Integer page;
  Integer numberOfElements;
  List<ProductDto> content;

}