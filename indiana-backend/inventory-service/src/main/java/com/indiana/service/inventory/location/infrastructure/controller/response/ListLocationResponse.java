package com.indiana.service.inventory.location.infrastructure.controller.response;

import com.indiana.service.inventory.location.application.dto.LocationDto;
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
