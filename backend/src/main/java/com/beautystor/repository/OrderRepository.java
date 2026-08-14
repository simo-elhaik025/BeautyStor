package com.beautystor.repository;

import com.beautystor.entity.Order;
import com.beautystor.enm.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"items"})
    Page<Order> findByUserIdOrderByCreatedAtDesc(long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"items", "items.variant", "items.variant.product"})
    Optional<Order> findByIdAndUserId(long id, long userId);

    @EntityGraph(attributePaths = {"items", "user"})
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = {"items", "items.variant", "items.variant.product", "user"})
    @Override
    Optional<Order> findById(Long id);

    long countByStatus(OrderStatus status);

    @Query("select coalesce(sum(o.totalAmount), 0) from Order o where o.status = ?1")
    BigDecimal sumTotalAmountByStatus(OrderStatus status);
}
