package com.indiana.service.inventory.warehouse.domain;

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
@Table(name = "warehouses")
public class Warehouse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wah_id")
  private Long id;
  @Column(name = "wah_name")
  private String name;
  @Column(name = "wah_loc_id")
  private Long locationId;
  @CreationTimestamp
  @Column(name = "wah_created_at")
  private ZonedDateTime createdAt;
  @UpdateTimestamp
  @Column(name = "wah_updated_at")
  private ZonedDateTime updatedAt;
}
