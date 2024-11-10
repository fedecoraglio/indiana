package com.indiana.inventory.api.inventory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "inventories")
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inv_id")
  private Long id;
  @Column(name = "inv_quantity_available")
  private Long quantityAvailable;
  @Column(name = "inv_minimum_stock_level")
  private Long minimumStockLevel;
  @Column(name = "inv_maximum_stock_level")
  private Long maximumStockLevel;
  @Column(name = "inv_reorder_point")
  private Long reorderPoint;
  @Column(name = "inv_prod_id")
  private Long productId;
  @Column(name = "inv_wah_id")
  private Long warehouseId;
  @Column(name = "inv_created_at")
  @CreationTimestamp
  private ZonedDateTime createdAt;
  @UpdateTimestamp
  @Column(name = "inv_updated_at")
  private ZonedDateTime updatedAt;
}
