package com.indiana.service.inventory.location.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

  List<Location> findAllByIdIn(final List<Long> ids);
}
