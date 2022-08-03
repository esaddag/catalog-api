package com.example.catalogapi;

import com.example.catalogapi.model.Product;
import com.example.catalogapi.repository.ProductRepository;
import com.example.catalogapi.service.CatalogService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CatalogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CatalogService catalogService, ProductRepository productRepository) {
        return args -> {

            List<Product> list = new ArrayList<>();

            list.add(new Product("AKSESUAR","BELLONA","BELLONA","SEHPALAR","ARMINA ORTA SEHPA", "22ANA33021P1P","", 1220.5, 1200.0, 1610.0, 1770.0, 1550.0, 1870.0 , 2000.0, 2200.0, 2100.0, "",-1));
            list.add(new Product("MOBİLYA","DİĞER MOBİLYA","GÖKOĞLU","LAMBADERLER", "LM 180","18011","", 700.0, 350.0, 560.0, 620.0, 530.0, 650.0 , 690.0, 760.0, 740.0, "",-1));
            list.add(new Product("MOBİLYA","BELLONA","BELLONA","BAZA", "İMPERA BAZA","28IMBI6","180", 700.0, 350.0, 560.0, 620.0, 530.0, 650.0 , 690.0, 760.0, 740.0, "",-1));
            list.add(new Product("TEKSTİL","BELLONA","BELLONA","YASTIK", "LM 180","1801132","", 700.0, 350.0, 560.0, 620.0, 530.0, 650.0 , 690.0, 760.0, 740.0, "",-1));
            productRepository.saveAll(list);
            System.out.println("-----------------------------");
            productRepository.getCategories().stream().forEach(System.out::println);
            System.out.println("-----------------------------");
            productRepository.getBrands().stream().forEach(System.out::println);

        };
    }
}
