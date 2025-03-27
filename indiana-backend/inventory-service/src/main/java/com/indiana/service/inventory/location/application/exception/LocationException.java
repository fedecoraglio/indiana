package com.indiana.service.inventory.location.application.exception;

import lombok.Getter;

@Getter
public class LocationException extends RuntimeException {

  private LocationExceptionType type;

  public LocationException(final LocationExceptionType type) {
    super(type.getMessage());
    this.type = type;
  }

  public LocationException(final LocationExceptionType type, final String message) {
    super(message != null ? message : type.getMessage());
    this.type = type;
  }

  public LocationException(final LocationExceptionType type, final Throwable cause) {
    super(type.getMessage(), cause);
  }

  @Getter
  public enum LocationExceptionType {
    LOCATION_CANNOT_BE_NULL("location-1000", "Location cannot be null"),
    LOCATION_UNEXPECTED_ERROR("location-1001", "Unexpected error on location"),
    LOCATION_SIZE_PAGINATION_LIMIT("location-1002", "Pagination limit exceed"),
    LOCATION_SAVING_ERROR("location-1003", "Error saving location"),
    LOCATION_NAME_CANNOT_BE_NULL("location-1004", "Name is required");

    private final String code;
    private final String message;

    LocationExceptionType(final String code, final String message) {
      this.code = code;
      this.message = message;
    }
  }
}
