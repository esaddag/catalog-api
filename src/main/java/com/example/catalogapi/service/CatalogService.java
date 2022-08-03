package com.example.catalogapi.service;

import com.example.catalogapi.model.ItemPage;
import com.example.catalogapi.model.Product;
import org.json.JSONObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CatalogService {

    ItemPage<Product> find(int page, int size, String direction, String... sortBy);
    ItemPage<Product> findFiltered(String categories, String subCategories, String brands, String productGroups, String productSize, String name, String productCode, int page, int size, String direction, String... sortBy);
    Optional<Product> findById(long id);
    Product saveProduct(Product product);
    List<Product> saveProducts(List<Product> product);
    boolean deleteProduct(long id);
    List<Product> getProducts();
    boolean checkExistsById(long id);
    JSONObject getFilters();
    public List<Product> importExcel(MultipartFile readExcelDataFile);



}
