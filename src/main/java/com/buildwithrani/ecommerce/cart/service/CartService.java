package com.buildwithrani.ecommerce.cart.service;

import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartStatus;
import com.buildwithrani.ecommerce.cart.repository.CartRepository;
import com.buildwithrani.ecommerce.cart.repository.CartItemRepository;
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
     * Fetch the user's ACTIVE cart.
     * If none exists, create a new one.
     */
    @Transactional
    public Cart getOrCreateActiveCart(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> createNewCart(userId));
    }

    /**
     * Fetch the user's ACTIVE cart.
     * Throws exception if not found.
     */
    public Cart getActiveCart(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
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

    private Cart createNewCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus(CartStatus.ACTIVE);
        cart.setTotalPrice(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }
}
