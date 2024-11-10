package com.indiana.inventory.api.location.application.usecase;

import com.indiana.inventory.api.location.application.dto.LocationDto;
import com.indiana.inventory.api.location.application.exception.LocationException;
import com.indiana.inventory.api.location.application.exception.LocationException.LocationExceptionType;
import com.indiana.inventory.api.location.application.mapper.LocationMapper;
import com.indiana.inventory.api.location.application.policy.SaveOrUpdateLocationPolicy;
import com.indiana.inventory.api.location.domain.Location;
import com.indiana.inventory.api.location.domain.LocationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Component
public class SaveOrUpdateLocationUseCase {

  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;
  private final SaveOrUpdateLocationPolicy saveOrUpdateLocationPolicy;

  @Transactional
  public LocationDto execute(final LocationDto dto) {
    try {
      log.info("Starting updating inventory");
      saveOrUpdateLocationPolicy.check(dto);
      final Location entity = locationMapper.toLocation(dto);
      final Location locationSaved = locationRepository.save(entity);
      log.info("Ending updating inventory. Id: {}", locationSaved.getId());
      return locationMapper.toLocationDto(locationSaved);
    } catch (final LocationException e) {
      log.error("Error updating location", e);
      throw e;
    } catch (final Exception e) {
      throw new LocationException(LocationExceptionType.LOCATION_SAVING_ERROR, e);
    }
  }
}
