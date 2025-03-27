package com.indiana.service.inventory.product.domain;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

  List<Product> findByCodeIgnoreCaseOrBarcodeIgnoreCaseOrNameIgnoreCase(final String code,
      final String barcode, final String name, final PageRequest request);
}
