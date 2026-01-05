package com.buildwithrani.ecommerce.order.controller;

import com.buildwithrani.ecommerce.order.model.Order;
import com.buildwithrani.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Create Order (Cart → Order)
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(Authentication authentication) {

        String userEmail = authentication.getName();

        Order order = orderService.createOrder(userEmail);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * Get Order History (My Orders)
     */
    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {

        String userEmail = authentication.getName();

        List<Order> orders = orderService.getOrdersByUser(userEmail);

        return ResponseEntity.ok(orders);
    }

    /**
     * Get Single Order
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long orderId,
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        Order order = orderService.getOrderById(orderId, userEmail);

        return ResponseEntity.ok(order);
    }
}
