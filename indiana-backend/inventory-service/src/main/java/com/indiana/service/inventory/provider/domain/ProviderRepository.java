package com.indiana.service.inventory.provider.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

  List<Provider> findAllByIdIn(final List<Long> ids);
}
