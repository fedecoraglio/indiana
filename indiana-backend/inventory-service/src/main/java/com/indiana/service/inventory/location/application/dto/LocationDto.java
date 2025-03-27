package com.indiana.service.inventory.location.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LocationDto {

  private final Long id;
  private final String name;
  private final String address;
}
