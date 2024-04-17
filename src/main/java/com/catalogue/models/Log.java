package com.catalogue.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Log implements Comparable<Log> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private LocalDateTime timestamp;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private CatalogueUser user;

  private String action;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Override
  public int compareTo(Log o) {
    return timestamp.compareTo(o.timestamp);
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public CatalogueUser getUser() {
    return user;
  }

  public void setUser(CatalogueUser user) {
    this.user = user;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
