package com.indiana.inventory.api.location.infrastructure.exception;

import com.indiana.inventory.api.location.application.exception.LocationException;
import lombok.Getter;

@Getter
public class LocationInfraException extends RuntimeException {

  private final LocationException locationException;

  public LocationInfraException(final LocationException locationException) {
    super(locationException.getMessage(), locationException);
    this.locationException = locationException;
  }
}
