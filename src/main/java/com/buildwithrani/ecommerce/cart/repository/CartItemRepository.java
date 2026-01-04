package com.buildwithrani.ecommerce.cart.repository;

import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Fetch all items belonging to a specific cart
    List<CartItem> findByCart(Cart cart);

    // Find a cart item by cart and product (used to merge quantities)
    Optional<CartItem> findByCartAndProductId(Cart cart, Long productId);

    // Remove all items when a cart is cleared or checked out
    void deleteByCart(Cart cart);
}
