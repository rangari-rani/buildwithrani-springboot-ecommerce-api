package com.buildwithrani.ecommerce.order.repository;

import com.buildwithrani.ecommerce.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Usually not needed directly, but useful for extensions
    List<OrderItem> findByOrderId(Long orderId);
}
