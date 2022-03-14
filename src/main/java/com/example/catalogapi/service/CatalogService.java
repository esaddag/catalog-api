package com.example.catalogapi.service;

import com.example.catalogapi.model.ItemPage;
import com.example.catalogapi.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CatalogService {

    ItemPage<Product> find(int page, int size, String direction, String... sortBy);
    Optional<Product> findById(long id);
    Product addProduct(Product product);
    boolean deleteProduct(Product product);
    List<Product> getProducts();


}
