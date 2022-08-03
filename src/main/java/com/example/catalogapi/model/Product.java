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
/*@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"category", "subCategory","brand","productName", "productCode"})
})*/
public class Product {

    public Product(String category, String subCategory, String brand, String productGroup, String productName, String productCode, String productSize, Double inputPrice, Double discountInputPrice, Double cashPrice, Double cardPrice, Double minPrice, Double burgan12Price, Double burgan18Price, Double openPrice, Double burgan24Price, String tagSize, int piece) {
        this.category = category;
        this.subCategory = subCategory;
        this.brand = brand;
        this.productGroup = productGroup;
        this.productName = productName;
        this.productCode = productCode;
        this.productSize = productSize;
        this.inputPrice = inputPrice;
        this.discountInputPrice = discountInputPrice;
        this.cashPrice = cashPrice;
        this.cardPrice = cardPrice;
        this.minPrice = minPrice;
        this.burgan12Price = burgan12Price;
        this.burgan18Price = burgan18Price;
        this.openPrice = openPrice;
        this.burgan24Price = burgan24Price;
        this.tagSize = tagSize;
        this.piece = piece;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;

    private String subCategory;

    private String brand;

    private String productGroup;

    private String productName;

    private String productCode;

    private String productSize;

    private Double inputPrice;

    private Double discountInputPrice;

    private Double cashPrice;

    private Double cardPrice;

    private Double minPrice;

    private Double burgan12Price;

    private Double burgan18Price;

    private Double openPrice;

    private Double burgan24Price;

    private String tagSize;

    private int piece;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp entryTime;

    @UpdateTimestamp
    private Timestamp updateTime;
}
