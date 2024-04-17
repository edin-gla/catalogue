package com.catalogue.controllers;

import com.catalogue.models.Tag;
import com.catalogue.repositories.ProductRepository;
import com.catalogue.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/tag")
public class TagController {

  @Autowired
  TagRepository tagRepository;

  @Autowired
  ProductRepository productRepository;

  @GetMapping("/list")
  public String getTags(Model m) {
    List<Tag> tags = tagRepository.findAll();
    m.addAttribute("tags", tags);
    return "tag/tags.html";
  }

  @GetMapping("/add")
  public String getTagAdd() {
    return "tag/tag-add.html";
  }

  @GetMapping("/edit/{id}")
  public String getTagEdit(Model m, @PathVariable Long id) {
    Tag tag = tagRepository.findById(id).orElseThrow();
    m.addAttribute("tag", tag);
    return "tag/tag-edit.html";
  }

  @PostMapping()
  public RedirectView postTag(String name, String color) {
    Tag tag = new Tag();
    tag.setName(name);
    tag.setColor(color);
    tagRepository.save(tag);
    return new RedirectView("/tag/list");
  }

  @PutMapping("/{id}")
  public RedirectView putTag(@PathVariable Long id, String name, String color) {
    Tag tag = tagRepository.findById(id).orElseThrow();
    tag.setName(name);
    tag.setColor(color);
    tagRepository.save(tag);
    return new RedirectView("/tag/list");
  }

  @DeleteMapping("/{id}")
  public RedirectView deleteTag(@PathVariable Long id) {
    Tag tag = tagRepository.findById(id).orElseThrow();
    tag.getProducts().forEach(product -> {
      product.getTags().remove(tag);
      productRepository.save(product);
    });
    tagRepository.deleteById(id);
    return new RedirectView("/tag/list");
  }

}
