package com.example.catalogapi.dto;

import com.example.catalogapi.model.Brand;
import com.example.catalogapi.model.Category;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String productCode;
    private String category;
    private String brand;
    private String productGroup;
    private String productType;
    private String productName;
    private Double price;
    private Timestamp entryTime;
    private Timestamp updateTime;
}
