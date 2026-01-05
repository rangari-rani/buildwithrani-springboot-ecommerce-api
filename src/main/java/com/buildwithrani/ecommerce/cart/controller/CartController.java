package com.buildwithrani.ecommerce.cart.controller;

import com.buildwithrani.ecommerce.auth.security.CustomUserDetails;
import com.buildwithrani.ecommerce.cart.dto.*;
import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartItem;
import com.buildwithrani.ecommerce.cart.service.CartItemService;
import com.buildwithrani.ecommerce.cart.service.CartService;
import com.buildwithrani.ecommerce.cart.repository.CartItemRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CartItemRepository cartItemRepository;

    public CartController(CartService cartService,
                          CartItemService cartItemService,
                          CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Get current user's cart.
     */
    @GetMapping
    public CartResponseDTO getCart(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String userEmail = userDetails.getUsername(); // email from JWT

        Cart cart = cartService.getOrCreateActiveCart(userEmail);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        return mapToCartResponse(cart, items);
    }



    /**
     * Add item to cart.
     */
    @PostMapping("/items")
    public void addItemToCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody AddToCartRequestDTO request
    ) {

        String userEmail = userDetails.getUsername(); // or getEmail()

        cartItemService.addItemToCart(
                userEmail,
                request.getProductId(),
                request.getQuantity()
        );
    }



    /**
     * Update cart item quantity.
     */
    @PutMapping("/items/{itemId}")
    public void updateCartItem(@PathVariable Long itemId,
                               @RequestBody UpdateCartItemRequestDTO request) {
        cartItemService.updateItemQuantity(itemId, request.getQuantity());
    }

    /**
     * Remove item from cart.
     */
    @DeleteMapping("/items/{itemId}")
    public void removeCartItem(@PathVariable Long itemId) {
        cartItemService.removeItem(itemId);
    }

    // ---------- mapping helpers ----------

    private CartResponseDTO mapToCartResponse(Cart cart, List<CartItem> items) {
        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(cart.getId());
        response.setTotalPrice(cart.getTotalPrice());

        List<CartItemResponseDTO> itemDTOs = items.stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());

        response.setItems(itemDTOs);
        return response;
    }

    private CartItemResponseDTO mapToCartItemResponse(CartItem item) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setItemId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setPrice(item.getProductPrice());
        dto.setQuantity(item.getQuantity());
        dto.setItemTotal(item.getItemTotal());
        return dto;
    }
}
