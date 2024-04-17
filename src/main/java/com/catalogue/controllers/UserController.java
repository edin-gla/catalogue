package com.catalogue.controllers;

import com.catalogue.models.CatalogueUser;
import com.catalogue.repositories.CatalogueUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  CatalogueUserRepository catalogueUserRepository;

  @GetMapping()
  public String getUserInfo(Model m, Principal p) {
    CatalogueUser user = catalogueUserRepository.findByUsername(p.getName());
    m.addAttribute("username", user.getUsername());
    m.addAttribute("dateOfBirth", user.getDateOfBirth());
    m.addAttribute("userId", user.getId());

    return "/user-info.html";
  }

  @PutMapping("/{id}")
  public RedirectView editUserInfo(Model m, Principal p, @PathVariable Long id, String username,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth) {
    CatalogueUser catalogueUser = catalogueUserRepository.findById(id).orElseThrow();
    boolean isUsernameUpdated = false;
    if (!catalogueUser.getUsername().equals(username)) {
      isUsernameUpdated = true;
      catalogueUser.setUsername(username);
    }
    catalogueUser.setDateOfBirth(dateOfBirth);
    catalogueUserRepository.save(catalogueUser);

    if(isUsernameUpdated) {
      SecurityContextHolder.clearContext(); // log out user
      return new RedirectView("/auth/login");
    }
    return new RedirectView("/");
  }

}
