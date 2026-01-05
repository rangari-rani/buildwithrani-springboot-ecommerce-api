package com.buildwithrani.ecommerce.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateOrderResponseDTO {
    private Long orderId;
    private BigDecimal totalAmount;
}
