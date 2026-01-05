package com.buildwithrani.ecommerce.cart.service;

import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartItem;
import com.buildwithrani.ecommerce.cart.repository.CartItemRepository;
import com.buildwithrani.ecommerce.product.model.Product;
import com.buildwithrani.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public CartItemService(CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    /**
     * Add a product to the user's cart.
     * If the product already exists in cart, increment quantity.
     */
    @Transactional
    public void addItemToCart(String userEmail, Long productId, Integer quantity) {

        // 1. Get or create ACTIVE cart using EMAIL
        Cart cart = cartService.getOrCreateActiveCart(userEmail);

        // 2. Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));

        // 3. Find or create CartItem
        CartItem cartItem = cartItemRepository
                .findByCartAndProductId(cart, productId)
                .orElseGet(() -> createNewCartItem(cart, product));

        // 4. Update quantity & totals
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setItemTotal(
                cartItem.getProductPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );

        cartItemRepository.save(cartItem);

        // 5. Recalculate cart total
        recalculateCartTotal(cart);
    }


    /**
     * Update quantity of an existing cart item.
     */
    @Transactional
    public void updateItemQuantity(Long cartItemId, Integer quantity) {

        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItem.setItemTotal(
                cartItem.getProductPrice().multiply(BigDecimal.valueOf(quantity))
        );

        cartItemRepository.save(cartItem);

        recalculateCartTotal(cartItem.getCart());
    }

    /**
     * Remove an item from the cart.
     */
    @Transactional
    public void removeItem(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Cart item not found"));

        Cart cart = cartItem.getCart();

        cartItemRepository.delete(cartItem);

        recalculateCartTotal(cart);
    }

    // ---------- private helpers ----------

    private CartItem createNewCartItem(Cart cart, Product product) {
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductPrice(product.getPrice());
        item.setQuantity(0);
        item.setItemTotal(BigDecimal.ZERO);
        return item;
    }

    private void recalculateCartTotal(Cart cart) {
        BigDecimal total = cartItemRepository.findByCart(cart).stream()
                .map(CartItem::getItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(total);
    }
}
