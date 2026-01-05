package com.buildwithrani.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
}
