package com.example.catalogapi.mappers;

import com.example.catalogapi.dto.BrandDTO;
import com.example.catalogapi.dto.CategoryDTO;
import com.example.catalogapi.dto.ProductDTO;
import com.example.catalogapi.model.Brand;
import com.example.catalogapi.model.Category;
import com.example.catalogapi.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO productToProductDTO(Product product);
    List<ProductDTO> productToProductDTO(List<Product> product);
    CategoryDTO categoryToCategoryDTO(Category category);
    List<CategoryDTO> categoryToCategoryDTO(List<Category> category);
    BrandDTO brandToBrandDTO(Brand brand);
    List<BrandDTO> brandToBrandDTO(List<Brand> brand);



    List<Product> productDTOToProduct(List<ProductDTO> product);

}
