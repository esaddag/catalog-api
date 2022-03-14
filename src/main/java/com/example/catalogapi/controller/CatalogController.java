package com.example.catalogapi.controller;


import com.example.catalogapi.model.Product;
import com.example.catalogapi.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


/**
 * Controller of app related to product. Path starts with /product.
 */
@RestController()
@RequestMapping("catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping("/")
    public ResponseEntity find(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sort,
                               @RequestParam(defaultValue = "asc") String direction,
                               @RequestBody(required = false) Product product){

        return ResponseEntity.ok().body(catalogService.find(page, size, direction, sort.split(",")));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity product(@PathVariable long id){
        return ResponseEntity.ok().body(catalogService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/save").toUriString());
        return ResponseEntity.created(uri).body(catalogService.addProduct(product));
    }



}
