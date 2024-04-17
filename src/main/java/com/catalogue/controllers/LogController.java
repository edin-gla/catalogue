package com.catalogue.controllers;

import com.catalogue.models.Log;
import com.catalogue.models.Product;
import com.catalogue.repositories.ProductRepository;
import com.catalogue.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

  @Autowired
  TagRepository tagRepository;

  @Autowired
  ProductRepository productRepository;

  @GetMapping("/{id}")
  public String getProductLogs(Model m, Principal p, @PathVariable Long id) {
    Product product = productRepository.findById(id).orElseThrow();
    product.getLogs().forEach(log -> {
      if (!log.getUser().getUsername().equals(p.getName())) {
        log.setUser(null);
      }
    });
    List<Log> logs = new ArrayList<>(product.getLogs());
    Collections.sort(logs);
    m.addAttribute("productName", product.getName());
    m.addAttribute("logs", logs);
    return "log/logs.html";
  }

}
