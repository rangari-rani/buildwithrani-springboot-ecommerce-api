package com.buildwithrani.ecommerce.cart.service;

import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartStatus;
import com.buildwithrani.ecommerce.cart.repository.CartItemRepository;
import com.buildwithrani.ecommerce.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Fetch the user's ACTIVE cart by EMAIL.
     * If none exists, create a new one.
     */
    @Transactional
    public Cart getOrCreateActiveCart(String userEmail) {

        return cartRepository.findByUserEmailAndStatus(userEmail, CartStatus.ACTIVE)
                .orElseGet(() -> createNewCart(userEmail));
    }

    /**
     * Fetch the user's ACTIVE cart by EMAIL.
     * Throws exception if not found.
     */
    public Cart getActiveCart(String userEmail) {

        return cartRepository.findByUserEmailAndStatus(userEmail, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("Active cart not found for user"));
    }

    /**
     * Clear all items from the cart and reset total.
     */
    @Transactional
    public void clearCart(Cart cart) {
        cartItemRepository.deleteByCart(cart);
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    /**
     * Mark cart as CHECKED_OUT after order placement.
     */
    @Transactional
    public void markCartAsCheckedOut(Cart cart) {
        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);
    }

    // ---------- private helpers ----------

    private Cart createNewCart(String userEmail) {

        Cart cart = new Cart();
        cart.setUserEmail(userEmail);
        cart.setStatus(CartStatus.ACTIVE);
        cart.setTotalPrice(BigDecimal.ZERO);

        return cartRepository.save(cart);
    }
}
