package com.buildwithrani.ecommerce.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    private String name;
    private String slug;
    private String description;

    private BigDecimal price;
    private Integer stock;

    private Boolean isFeatured;

    private String image;

    private Long categoryId;
}
