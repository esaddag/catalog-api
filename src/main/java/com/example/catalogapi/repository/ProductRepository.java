package com.example.catalogapi.repository;

import com.example.catalogapi.model.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {



    //@Cacheable("categories")
    @Query(value = "select distinct category from Product where category is not null")
    List<String> getCategories();

    @Query(value = "select distinct subCategory from Product where subCategory is not null")
    List<String> getSubCategories();

    //@Cacheable("brands")
    @Query(value = "select distinct brand from Product where brand is not null")
    List<String> getBrands();

    //@Cacheable("productGroups")
    @Query(value = "select distinct productGroup from Product where productGroup is not null")
    List<String> getProductGroups();

    //@Cacheable("productTypes")
    @Query(value = "select distinct productName from Product")
    List<String> getProductName();

    //@Cacheable("productTypes")
    @Query(value = "select distinct productCode from Product")
    List<String> getProductCode();

    //@Cacheable("productTypes")
    @Query(value = "select distinct productSize from Product where productSize IS NOT NULL and productSize<>'' ")
    List<String> getProductSize();

    @Override
    <S extends Product> List<S> saveAll(Iterable<S> entities);



    Page<Product>  findProductsByCategoryContainingAndSubCategoryContainingAndBrandContainingAndProductGroupContainingAndProductSizeContainingAndProductNameContainingAndProductCodeContaining(String categories, String subCategories, String brands, String productGroups, String sizes, String name,String productCode, Pageable pageable);



}

