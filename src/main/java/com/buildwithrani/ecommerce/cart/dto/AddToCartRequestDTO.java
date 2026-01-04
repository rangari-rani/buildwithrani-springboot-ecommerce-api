package com.buildwithrani.ecommerce.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequestDTO {
    private Long productId;
    private Integer quantity;
}
