package com.indiana.product.api.inventory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "inventories")
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inv_id")
  private Long id;
  @Column(name = "inv_quantity_available")
  private Integer quantityAvailable;
  @Column(name = "inv_minimum_stock_level")
  private Integer minimumStockLevel;
  @Column(name = "inv_maximum_stock_level")
  private Integer maximumStockLevel;
  @Column(name = "inv_reorder_point")
  private Integer reorderPoint;
  @Column(name = "inv_prod_id")
  private Long productId;
  @Column(name = "inv_wah_id")
  private Long warehouseId;
}
