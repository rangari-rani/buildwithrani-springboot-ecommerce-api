package com.buildwithrani.ecommerce.order.controller;

import com.buildwithrani.ecommerce.order.dto.CreateOrderResponseDTO;
import com.buildwithrani.ecommerce.order.dto.OrderResponseDTO;
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
    public ResponseEntity<CreateOrderResponseDTO> createOrder(
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        CreateOrderResponseDTO response =
                orderService.createOrder(userEmail);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get Order History (My Orders)
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        List<OrderResponseDTO> orders =
                orderService.getOrdersByUser(userEmail);

        return ResponseEntity.ok(orders);
    }

    /**
     * Get Single Order
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId,
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        OrderResponseDTO order =
                orderService.getOrderById(orderId, userEmail);

        return ResponseEntity.ok(order);
    }
}
