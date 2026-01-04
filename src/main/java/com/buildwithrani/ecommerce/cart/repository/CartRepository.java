package com.buildwithrani.ecommerce.cart.repository;

import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Fetch the user's ACTIVE cart (enforces one active cart per user)
    Optional<Cart> findByUserIdAndStatus(Long userId, CartStatus status);

    // Check if the user already has an ACTIVE cart
    boolean existsByUserIdAndStatus(Long userId, CartStatus status);
}
