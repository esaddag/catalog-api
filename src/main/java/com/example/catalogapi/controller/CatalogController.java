package com.example.catalogapi.controller;


import com.example.catalogapi.dto.BrandDTO;
import com.example.catalogapi.dto.ProductDTO;
import com.example.catalogapi.mappers.ProductMapper;
import com.example.catalogapi.model.Brand;
import com.example.catalogapi.model.ItemPage;
import com.example.catalogapi.model.Product;
import com.example.catalogapi.repository.ProductRepository;
import com.example.catalogapi.service.CatalogService;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Controller of app related to product. Path starts with /product.
 */
@CrossOrigin("http://localhost:3000")
@RestController()
@RequestMapping("catalog")
public class CatalogController {

    private final CatalogService catalogService;
    private final ProductMapper mapper;

    public CatalogController(CatalogService catalogService, ProductMapper mapper, ProductRepository productRepository) {
        this.catalogService = catalogService;
        this.mapper = mapper;
    }

    @PostMapping("/products")
    public ResponseEntity<List<Product>> saveProducts(@RequestBody List<ProductDTO> productDTOs){
        List<Product> products = mapper.productDTOToProduct(productDTOs);
        //mapper.brandToBrandDTO(products.stream().flatMap(product -> product.getBrand()));
        //List<Brand> brands = products.stream().flatMap(e -> Stream.of(e.getBrand()).filter(brand -> brand != null)).collect(Collectors.toList());
        /*List<BrandDTO> brandDTOS = mapper.brandToBrandDTO(brands);
        for(Brand b: x){
            System.out.println(b.toString());
        }*/
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/save").toUriString());
        List<Product> result = catalogService.saveProducts(products);
        if(result != null){
            return ResponseEntity.created(uri).body(result);
        }
        else{
            return ResponseEntity.badRequest().build();
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/find")
    public ResponseEntity find(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sort,
                               @RequestParam(defaultValue = "asc") String direction,
                               @RequestParam(defaultValue = "") String category,
                               @RequestParam(defaultValue = "") String subCategory,
                               @RequestParam(defaultValue = "") String brand,
                               @RequestParam(defaultValue = "") String productGroup,
                               @RequestParam(defaultValue = "") String productSize,
                               @RequestParam(defaultValue = "") String productName,
                               @RequestParam(defaultValue = "") String productCode

                               ){

        return ResponseEntity.ok().body(catalogService.findFiltered(category, subCategory, brand, productGroup, productSize, productName, productCode, page, size, direction, sort.split(",")));
    }

    @GetMapping("/products")
    public ResponseEntity<ItemPage<Product>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sort,
                                                @RequestParam(defaultValue = "asc") String direction){

        return ResponseEntity.ok().body(catalogService.find(page, size, direction, sort.split(",")));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        Optional<Product> result = catalogService.findById(id);
        if(result.isPresent()){
            return ResponseEntity.ok().body(result.get());
        }else{
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @RequestBody Product product){
        if (catalogService.checkExistsById(id)){
            product.setId(id);
            Product result = catalogService.saveProduct(product);
            if (result != null) {
                return ResponseEntity.noContent().build();
            }else{
                return ResponseEntity.badRequest().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }


    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        boolean result = catalogService.deleteProduct(id);

        if(result)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }


    @GetMapping("/filters")
    public ResponseEntity getFilters(){

        JSONObject filters = catalogService.getFilters();
        return ResponseEntity.ok().body(filters.toString());
    }

    @PostMapping("/importExcel")
    public ResponseEntity<List<Product>> importExcel(@RequestParam("file") MultipartFile reapExcelDataFile){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/importExcel").toUriString());
        return ResponseEntity.created(uri).body(catalogService.importExcel(reapExcelDataFile));
    }






}
