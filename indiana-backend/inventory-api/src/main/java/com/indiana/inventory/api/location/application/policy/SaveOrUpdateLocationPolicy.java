package com.indiana.inventory.api.location.application.policy;

import com.indiana.inventory.api.location.application.dto.LocationDto;
import com.indiana.inventory.api.location.application.exception.LocationException;
import com.indiana.inventory.api.location.application.exception.LocationException.LocationExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SaveOrUpdateLocationPolicy {

  public void check(final LocationDto locationDto) {
    if (locationDto == null) {
      throw new LocationException(LocationExceptionType.LOCATION_CANNOT_BE_NULL);
    }
    if ("".equals(locationDto.getName())) {
      throw new LocationException(LocationExceptionType.LOCATION_NAME_CANNOT_BE_NULL);
    }
  }
}
