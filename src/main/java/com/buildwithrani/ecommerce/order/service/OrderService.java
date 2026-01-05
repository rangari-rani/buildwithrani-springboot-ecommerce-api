package com.buildwithrani.ecommerce.order.service;

import com.buildwithrani.ecommerce.cart.model.Cart;
import com.buildwithrani.ecommerce.cart.model.CartItem;
import com.buildwithrani.ecommerce.cart.model.CartStatus;
import com.buildwithrani.ecommerce.cart.repository.CartItemRepository;
import com.buildwithrani.ecommerce.cart.repository.CartRepository;
import com.buildwithrani.ecommerce.order.model.Order;
import com.buildwithrani.ecommerce.order.model.OrderItem;
import com.buildwithrani.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    /**
     * Cart → Order conversion
     */
    @Transactional
    public Order createOrder(String userEmail) {

        Cart cart = cartRepository.findByUserEmailAndStatus(userEmail, CartStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Active cart not found"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .userEmail(userEmail)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = OrderItem.builder()
                    .productId(cartItem.getProductId())
                    .productName(cartItem.getProductName())
                    .price(cartItem.getProductPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItem.setOrder(order);
            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(cartItem.getItemTotal());
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    /**
     * Order history (WITH items)
     */
    public List<Order> getOrdersByUser(String userEmail) {
        return orderRepository.findOrdersWithItemsByUserEmail(userEmail);
    }

    /**
     * Single order (WITH items + ownership check)
     */
    public Order getOrderById(Long orderId, String userEmail) {
        return orderRepository.findOrderWithItemsByIdAndUserEmail(orderId, userEmail)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
