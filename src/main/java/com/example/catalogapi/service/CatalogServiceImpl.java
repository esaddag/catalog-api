package com.example.catalogapi.service;

import com.example.catalogapi.mappers.ProductRowMapper;
import com.example.catalogapi.model.ItemPage;
import com.example.catalogapi.model.Pagination;
import com.example.catalogapi.model.Product;
import com.example.catalogapi.repository.ProductRepository;
import com.example.catalogapi.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.catalogapi.service.ErrorMessages.SAVE_FAILED;

/**
 * Product service
 * CRUD operations on products
 */
@Service @Slf4j
public class CatalogServiceImpl implements CatalogService {


    private final ProductRepository productRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public CatalogServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
    public ItemPage<Product> findFiltered(String category, String subCategory, String brand, String productGroup, String productSize, String name, String productCode, int page, int size, String direction, String... sortBy) {
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

        result = productRepository.findProductsByCategoryContainingAndSubCategoryContainingAndBrandContainingAndProductGroupContainingAndProductSizeContainingAndProductNameContainingAndProductCodeContaining(category.toUpperCase(Locale.forLanguageTag("tr-TR")), subCategory.toUpperCase(Locale.forLanguageTag("tr-TR")), brand.toUpperCase(Locale.forLanguageTag("tr-TR")) , productGroup.toUpperCase(Locale.forLanguageTag("tr-TR")), productSize.toUpperCase(Locale.forLanguageTag("tr-TR")), name.toUpperCase(Locale.forLanguageTag("tr-TR")), productCode.toUpperCase(Locale.forLanguageTag("tr-TR")), paging);

        return new ItemPage<>(result.getContent(), new Pagination(result.getTotalElements(), result.getTotalPages(), result.getSize(), result.getNumber()+1));


        /*String query = "Select * from product where ";
        List<Object> params = new ArrayList<Object>();
        
        if(categories.size()>0){
            query += " category in (?) and";
            params.add(categories);
        }

        if(subCategories.size()>0){
            query += " subCategory in (?) and";
            params.add(subCategories);
        }

        if(brands.size()>0){
            query += " brand in (";
            for(String x : brands){
                query += "?,";
                params.add(x);
            }
            query = query.substring(0,query.length()-1);
            query += ") and";
        }

        if(productGroups.size()>0){
            query += " productGroup in (?) and";
            params.add(productGroups);
        }*/

        /*if(category != null && !category.isEmpty()){
            query += " category like (?) and";
            params.add("%"+category.toUpperCase(Locale.forLanguageTag("tr-TR"))+"%");
        }
        if(subCategory != null && !subCategory.isEmpty()){
            query += " subCategory like (?) and";
            params.add("%"+subCategory.toUpperCase(Locale.forLanguageTag("tr-TR"))+"%");
        }
        if(brand != null && !brand.isEmpty()){
            query += " brand like (?) and";
            params.add("%"+brand.toUpperCase(Locale.forLanguageTag("tr-TR"))+"%");
        }
        if(productGroup != null && !productGroup.isEmpty()){
            query += " productGroup like (?) and";
            params.add("%"+productGroup.toUpperCase(Locale.forLanguageTag("tr-TR"))+"%");
        }
        if(productSize != null && !productSize.isEmpty()){
            query += " productSize like (?) and";
            params.add("%"+productSize.toUpperCase(Locale.forLanguageTag("tr-TR"))+"%");
        }
        if(name != null && !name.isEmpty()){
            query += " productName like (?) and";
            params.add("%"+name.toUpperCase(Locale.forLanguageTag("tr-TR"))+"%");
        }


        if (category.isEmpty() && subCategory.isEmpty() && brand.isEmpty() && productGroup.isEmpty() && productSize.isEmpty() && name.isEmpty()) {
            query = query.substring(0,query.length()-6);
        }else{
            query = query.substring(0,query.length()-4);
        }

        query +=";";


        result = jdbcTemplate.query(query, new ProductRowMapper(), params.toArray());*/


    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        Product savedProduct = null;
        try{
            savedProduct = productRepository.save(product);
        }catch (Exception e){
            log.error(SAVE_FAILED);
        }
        return savedProduct;
    }

    @Override
    public List<Product> saveProducts(List<Product> products) {
        List<Product> savedProducts = null;
        try{
            savedProducts = productRepository.saveAll(products);
            JSONObject r = getFilters();
            return savedProducts;
        }catch (Exception e){
            log.error(SAVE_FAILED);
            return null;
        }

    }

    @Override
    public boolean deleteProduct(long id) {
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean checkExistsById(long id) {
        return productRepository.existsById(id);
    }

    @Override
    public JSONObject getFilters() {

        List<String> brands = productRepository.getBrands();
        List<String> productGroups = productRepository.getProductGroups();
        List<String> productNames = productRepository.getProductName();
        List<String> sizes = productRepository.getProductSize();
        List<String> productCodes = productRepository.getProductCode();
        List<String> subCategories = productRepository.getSubCategories();
        List<String> categories = productRepository.getCategories();


        JSONObject filters = new JSONObject();
        filters.put("brands", brands);
        filters.put("sizes", sizes);
        filters.put("subCategories", subCategories);
        filters.put("productCodes", productCodes);
        filters.put("productGroups", productGroups);
        filters.put("productNames", productNames);
        filters.put("categories", categories);
        return filters;
    }

    @Override
    public List<Product> importExcel(MultipartFile readExcelDataFile) {
        ExcelUtils excelUtils = new ExcelUtils();
        List<Product> result = excelUtils.importExcel(readExcelDataFile);
        //System.out.println(result.size());

        List<Product> res = saveProducts(result); //productRepository.saveAll(result);


        return res;

    }
}
