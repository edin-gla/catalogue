package com.catalogue.controllers;

import com.catalogue.models.CatalogueUser;
import com.catalogue.models.Log;
import com.catalogue.models.Product;
import com.catalogue.models.Tag;
import com.catalogue.repositories.CatalogueUserRepository;
import com.catalogue.repositories.LogRepository;
import com.catalogue.repositories.ProductRepository;
import com.catalogue.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class ProductController {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  TagRepository tagRepository;

  @Autowired
  CatalogueUserRepository catalogueUserRepository;

  @Autowired
  private LogRepository logRepository;

  @GetMapping("/list")
  public String getProducts(Model m) {
    List<Product> products = productRepository.findAll();
    m.addAttribute("products", products);
    return "product/products.html";
  }

  @GetMapping("/add")
  public String getProductAdd(Model m) {
    List<Tag> tags = tagRepository.findAll();
    m.addAttribute("tags", tags);
    return "product/product-add.html";
  }

  @GetMapping("/edit/{id}")
  public String getProductEdit(Model m, Principal p, @PathVariable Long id) {
    Product product = productRepository.findById(id).orElseThrow();
    m.addAttribute("product", product);
    List<Tag> tags = tagRepository.findAll();
    tags.forEach(tag -> tag.setSelected(product.getTags().contains(tag)));
    m.addAttribute("tags", tags);

    CatalogueUser user = catalogueUserRepository.findByUsername(p.getName());
    Log log = new Log();
    log.setProduct(product);
    log.setUser(user);
    log.setAction("READ");
    log.setTimestamp(LocalDateTime.now());
    logRepository.save(log);

    return "product/product-edit.html";
  }

  @PostMapping()
  public RedirectView postProduct(Principal p, String name, Integer quantity, long[] tags) {
    Product product = new Product();
    product.setName(name);
    product.setQuantity(quantity);
    if (tags != null) {
      Set<Tag> tagSet = Arrays.stream(tags)
        .mapToObj((tagId) -> tagRepository.findById(tagId).orElseThrow())
        .collect(Collectors.toSet());
      product.setTags(tagSet);
    }
    productRepository.save(product);

    CatalogueUser user = catalogueUserRepository.findByUsername(p.getName());
    Log log = new Log();
    log.setProduct(product);
    log.setUser(user);
    log.setAction("CREATE");
    log.setTimestamp(LocalDateTime.now());
    logRepository.save(log);

    return new RedirectView("/product/list");
  }

  @PutMapping("/{id}")
  public RedirectView putProduct(Principal p, @PathVariable Long id, String name, Integer quantity, long[] tags) {
    Product product = productRepository.findById(id).orElseThrow();
    product.setName(name);
    product.setQuantity(quantity);
    if (tags != null) {
      Set<Tag> tagSet = Arrays.stream(tags)
        .mapToObj((tagId) -> tagRepository.findById(tagId).orElseThrow()).collect(Collectors.toSet());
      product.setTags(tagSet);
    }
    productRepository.save(product);

    CatalogueUser user = catalogueUserRepository.findByUsername(p.getName());
    Log log = new Log();
    log.setProduct(product);
    log.setUser(user);
    log.setAction("UPDATE");
    log.setTimestamp(LocalDateTime.now());
    logRepository.save(log);

    return new RedirectView("/product/list");
  }

  @DeleteMapping("/{id}")
  public RedirectView deleteProduct(@PathVariable Long id) {
    productRepository.deleteById(id);
    return new RedirectView("/product/list");
  }

}
