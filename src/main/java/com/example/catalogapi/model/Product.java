package com.example.catalogapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productCode;

    private String category;

    private String brand;

    private String productGroup;

    private String productType;

    private String productName;

    private Double price;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp entryTime;

    @UpdateTimestamp
    private Timestamp updateTime;
}
