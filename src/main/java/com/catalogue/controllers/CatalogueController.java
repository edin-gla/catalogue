package com.catalogue.controllers;

import com.catalogue.models.CatalogueUser;
import com.catalogue.repositories.CatalogueUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

@Controller
public class CatalogueController {

  @Autowired
  CatalogueUserRepository catalogueUserRepository;

  @GetMapping("/")
  public String getIndexPage(Model m, Principal p) {
    if (p != null) {
      String username = p.getName();
      CatalogueUser catalogueUser = catalogueUserRepository.findByUsername(username);

      m.addAttribute("username", username);
      m.addAttribute("userId", catalogueUser.getId());
      m.addAttribute("dateOfBirth", catalogueUser.getDateOfBirth());
    }

    return "index.html";
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public class ResourceNotFoundException extends RuntimeException {
    ResourceNotFoundException(String message) {
      super(message);
    }
  }

}
