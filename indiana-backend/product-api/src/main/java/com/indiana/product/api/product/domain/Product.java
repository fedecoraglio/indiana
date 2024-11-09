package com.indiana.product.api.product.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pro_id")
  private Long id;
  @Column(name = "pro_code")
  private String code;
  @Column(name = "pro_barcode")
  private String barcode;
  @Column(name = "pro_name")
  private String name;
  @Column(name = "pro_description")
  private String description;
  @Column(name = "pro_packed_weight")
  private Double packedWeight;
  @Column(name = "pro_packed_width")
  private Double packedWidth;
  @Column(name = "pro_packed_depth")
  private Double packedDepth;
  @Column(name = "pro_refrigerated")
  private Boolean refrigerated;
}
