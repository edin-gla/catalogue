package com.catalogue.repositories;

import com.catalogue.models.CatalogueUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogueUserRepository extends JpaRepository<CatalogueUser, Long> {
  CatalogueUser findByUsername(String username);
}
