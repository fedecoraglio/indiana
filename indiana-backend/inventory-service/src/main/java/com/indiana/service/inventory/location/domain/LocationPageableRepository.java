package com.indiana.service.inventory.location.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationPageableRepository extends PagingAndSortingRepository<Location, Long> {

  Page<Location> findByNameIgnoreCaseContaining(final String name, final Pageable pageable);
}
