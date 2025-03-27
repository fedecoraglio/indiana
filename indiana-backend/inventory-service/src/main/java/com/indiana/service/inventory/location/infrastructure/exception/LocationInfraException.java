package com.indiana.service.inventory.location.infrastructure.exception;

import com.indiana.service.inventory.location.application.exception.LocationException;
import lombok.Getter;

@Getter
public class LocationInfraException extends RuntimeException {

  private final LocationException locationException;

  public LocationInfraException(final LocationException locationException) {
    super(locationException.getMessage(), locationException);
    this.locationException = locationException;
  }
}
