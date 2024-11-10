package com.indiana.inventory.api.location.infrastructure.controller.response;

import com.indiana.inventory.api.location.application.dto.LocationDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListLocationResponse {

  Long totalElements;
  Integer totalPages;
  Integer pageSize;
  Integer page;
  Integer numberOfElements;
  List<LocationDto> content;

}
