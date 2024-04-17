package com.catalogue.models;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;
  int quantity;

  @ManyToMany
  @JoinTable (
    name = "product_tag",
    joinColumns = {@JoinColumn(name = "product_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  Set<Tag> tags = new HashSet<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<Log> logs;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public Set<Log> getLogs() {
    return logs;
  }
}
