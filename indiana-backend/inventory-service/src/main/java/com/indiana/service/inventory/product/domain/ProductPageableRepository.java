package com.indiana.service.inventory.product.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface ProductPageableRepository extends PagingAndSortingRepository<Product, Long> {

  Page<Product> findByCodeStartingWithOrBarcodeStartingWithOrNameStartingWithOrderById(
      final String code, final String barcode, final String name, final PageRequest request);
}
