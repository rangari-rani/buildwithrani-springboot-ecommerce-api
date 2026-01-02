package com.buildwithrani.ecommerce.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private Long id;

    private String name;
    private String slug;
    private String description;

    private BigDecimal price;

    private String image;

    private Long categoryId;
}
