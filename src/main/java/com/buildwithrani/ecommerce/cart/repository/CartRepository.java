package com.buildwithrani.ecommerce.cart.repository;

import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserEmailAndStatus(String userEmail, CartStatus status);
    boolean existsByUserEmailAndStatus(String userEmail, CartStatus status);

}
