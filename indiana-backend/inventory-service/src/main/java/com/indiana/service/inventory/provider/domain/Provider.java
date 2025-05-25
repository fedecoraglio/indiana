package com.indiana.service.inventory.provider.domain;

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
@Table(name = "providers")
public class Provider {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pro_id")
  private Long id;
  @Column(name = "pro_name")
  private String name;
  @Column(name = "pro_address")
  private String address;
  @CreationTimestamp
  @Column(name = "pro_created_at")
  private ZonedDateTime createdAt;
  @UpdateTimestamp
  @Column(name = "pro_updated_at")
  private ZonedDateTime updatedAt;
}
