package com.indiana.service.inventory.common.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Builder
@Getter
public class ListResponse<E> {

  Long totalElements;
  Integer totalPages;
  Integer pageSize;
  Integer page;
  Integer numberOfElements;
  List<E> content;

  public static <T> ListResponse<T> toListResponse(Page<T> page) {
    return ListResponse.<T>builder()
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .pageSize(page.getSize())
        .page(page.getNumber())
        .numberOfElements(page.getNumberOfElements())
        .content(page.getContent())
        .build();
  }


}
