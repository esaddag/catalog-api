package com.example.catalogapi.service;

import com.example.catalogapi.model.ItemPage;
import com.example.catalogapi.model.Pagination;
import com.example.catalogapi.model.Product;
import com.example.catalogapi.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Product service
 * CRUD operations on products
 */
@Service @Slf4j
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    ProductRepository productRepository;

    //todo: ürün bilgisine göre code oluştur
    /*private long createProductCode(Product product){

    }*/

    @Override
    public ItemPage<Product> find(int page, int size, String direction, String... sortBy) {

        Page<Product> result;
        Sort.Direction sortDirection;
        try{
            sortDirection  = Sort.Direction.fromString(direction);
        }catch (IllegalArgumentException e){
            log.error("Invalid sorting direction! Default value (asc) will be used. Valid values are 'asc' and 'desc'.");
            sortDirection = Sort.Direction.ASC;
        }
        if(page!=0)
            page--;
        Pageable paging = PageRequest.of(page, size,Sort.by(sortDirection, sortBy));

        result = productRepository.findAll(paging);

        return new ItemPage<>(result.getContent(), new Pagination(result.getTotalElements(), result.getTotalPages(), result.getSize(), result.getNumber()+1));
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Product product) {
        productRepository.delete(product);
        return true;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }





}
