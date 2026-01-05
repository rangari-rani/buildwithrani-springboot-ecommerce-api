package com.buildwithrani.ecommerce.order.repository;

import com.buildwithrani.ecommerce.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Order history with items
     */
    @Query("""
        select distinct o
        from Order o
        left join fetch o.items
        where o.userEmail = :userEmail
        order by o.createdAt desc
    """)
    List<Order> findOrdersWithItemsByUserEmail(
            @Param("userEmail") String userEmail
    );

    /**
     * Single order with items (ownership check)
     */
    @Query("""
        select o
        from Order o
        left join fetch o.items
        where o.id = :orderId
          and o.userEmail = :userEmail
    """)
    Optional<Order> findOrderWithItemsByIdAndUserEmail(
            @Param("orderId") Long orderId,
            @Param("userEmail") String userEmail
    );
}
