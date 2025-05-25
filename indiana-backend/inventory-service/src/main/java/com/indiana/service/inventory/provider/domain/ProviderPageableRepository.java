package com.indiana.service.inventory.provider.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderPageableRepository extends PagingAndSortingRepository<Provider, Long> {

  Page<Provider> findByNameIgnoreCaseContaining(final String name, final Pageable pageable);
}
