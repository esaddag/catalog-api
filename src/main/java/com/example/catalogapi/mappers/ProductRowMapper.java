package com.example.catalogapi.mappers;

import com.example.catalogapi.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {


    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("category"),
                rs.getString("subCategory"),
                rs.getString("brand"),
                rs.getString("productGroup"),
                rs.getString("productName"),
                rs.getString("productCode"),
                rs.getString("productSize"),
                rs.getDouble("inputPrice"),
                rs.getDouble("discountInputPrice"),
                rs.getDouble("cashPrice"),
                rs.getDouble("cardPrice"),
                rs.getDouble("minPrice"),
                rs.getDouble("burgan12Price"),
                rs.getDouble("burgan18Price"),
                rs.getDouble("openPrice"),
                rs.getDouble("burgan24Price"),
                rs.getString("tagSize"),
                rs.getInt("piece"),
                rs.getTimestamp("entryTime"),
                rs.getTimestamp("updateTime")

                );
    }
}
