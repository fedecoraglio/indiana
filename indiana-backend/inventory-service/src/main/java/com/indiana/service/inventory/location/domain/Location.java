package com.indiana.service.inventory.location.domain;

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
@Table(name = "locations")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "loc_id")
  private Long id;
  @Column(name = "loc_name")
  private String name;
  @Column(name = "loc_address")
  private String address;
  @CreationTimestamp
  @Column(name = "loc_created_at")
  private ZonedDateTime createdAt;
  @UpdateTimestamp
  @Column(name = "loc_updated_at")
  private ZonedDateTime updatedAt;
}
