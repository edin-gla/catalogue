package com.catalogue.controllers;

import com.catalogue.models.CatalogueUser;
import com.catalogue.repositories.CatalogueUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  CatalogueUserRepository catalogueUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  HttpServletRequest request;

  @GetMapping("/login")
  public String getLoginPage() {
    return "auth/login.html";
  }

  @GetMapping("/signup")
  public String getSignupPage() {
    return "auth/signup.html";
  }

  @PostMapping("/signup")
  public RedirectView postSignup(String username, String password,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth) {
    CatalogueUser catalogueUser = new CatalogueUser();
    catalogueUser.setUsername(username);
    catalogueUser.setDateOfBirth(dateOfBirth);
    String encryptedPassword = passwordEncoder.encode(password);
    catalogueUser.setPassword(encryptedPassword);
    catalogueUserRepository.save(catalogueUser);
    return new RedirectView("/");
  }

}
