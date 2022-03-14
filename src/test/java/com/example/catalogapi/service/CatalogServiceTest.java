package com.example.catalogapi.service;

import com.example.catalogapi.model.Pagination;
import com.example.catalogapi.model.Product;
import com.example.catalogapi.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
@SpringBootTest
class CatalogServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    CatalogService catalogService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void find() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());

        PageImpl<Product> productPage = new PageImpl<Product>(productList);
        Mockito.when(this.productRepository.findAll((Pageable) any())).thenReturn(productPage);
        int result = catalogService.find(1, 3, "asc", "id").getData().size();
        Assert.assertEquals(3, result );

    }

    @Test
    void findById() {
        Product testProduct = new Product();
        testProduct.setId(1L);
        Mockito.when(this.productRepository.findById(any())).thenReturn(Optional.of(testProduct));
        long result = catalogService.findById(1).get().getId();
        Assert.assertEquals(1, result );
    }

    @Test
    void addProduct() {
        Product testProduct = new Product();
        testProduct.setId(123L);
        Mockito.when(this.productRepository.save(any())).thenReturn(testProduct);
        long result = catalogService.addProduct(testProduct).getId();
        Assert.assertEquals(123, result );
    }

    @Test
    void deleteProduct() {
        Product testProduct = new Product();
        testProduct.setId(123L);
        Assert.assertTrue( catalogService.deleteProduct(testProduct) );
    }

    @Test
    void getProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());
        Mockito.when(this.productRepository.findAll()).thenReturn(productList);
        Assert.assertEquals( catalogService.getProducts().size(), 3 );
    }
}