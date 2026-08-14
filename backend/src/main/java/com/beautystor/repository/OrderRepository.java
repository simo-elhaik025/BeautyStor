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
import java.time.LocalDateTime;
import java.util.List;
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

    @Query("""
            select new com.beautystor.dto.statistics.DailyOrderStatResponse(
                year(o.createdAt),
                month(o.createdAt),
                day(o.createdAt),
                count(o))
            from Order o
            where o.createdAt >= ?1 and o.createdAt < ?2
            group by year(o.createdAt), month(o.createdAt), day(o.createdAt)
            order by year(o.createdAt), month(o.createdAt), day(o.createdAt)
            """)
    List<com.beautystor.dto.statistics.DailyOrderStatResponse> findDailyOrderStats(
            LocalDateTime startInclusive,
            LocalDateTime endExclusive);

    @Query("""
            select new com.beautystor.dto.statistics.DailyRevenueStatResponse(
                year(o.createdAt),
                month(o.createdAt),
                day(o.createdAt),
                coalesce(sum(o.totalAmount), 0))
            from Order o
            where o.status = com.beautystor.enm.OrderStatus.DELIVERED
            and o.createdAt >= ?1 and o.createdAt < ?2
            group by year(o.createdAt), month(o.createdAt), day(o.createdAt)
            order by year(o.createdAt), month(o.createdAt), day(o.createdAt)
            """)
    List<com.beautystor.dto.statistics.DailyRevenueStatResponse> findDailyDeliveredRevenue(
            LocalDateTime startInclusive,
            LocalDateTime endExclusive);

    @Query("""
            select new com.beautystor.dto.statistics.DailyOrderStatResponse(
                year(o.createdAt),
                month(o.createdAt),
                1,
                count(o))
            from Order o
            where o.createdAt >= ?1 and o.createdAt < ?2
            group by year(o.createdAt), month(o.createdAt)
            order by year(o.createdAt), month(o.createdAt)
            """)
    List<com.beautystor.dto.statistics.DailyOrderStatResponse> findMonthlyOrderStats(
            LocalDateTime startInclusive,
            LocalDateTime endExclusive);

    @Query("""
            select new com.beautystor.dto.statistics.DailyRevenueStatResponse(
                year(o.createdAt),
                month(o.createdAt),
                1,
                coalesce(sum(o.totalAmount), 0))
            from Order o
            where o.status = com.beautystor.enm.OrderStatus.DELIVERED
            and o.createdAt >= ?1 and o.createdAt < ?2
            group by year(o.createdAt), month(o.createdAt)
            order by year(o.createdAt), month(o.createdAt)
            """)
    List<com.beautystor.dto.statistics.DailyRevenueStatResponse> findMonthlyDeliveredRevenue(
            LocalDateTime startInclusive,
            LocalDateTime endExclusive);

    @Query("""
            select new com.beautystor.dto.statistics.OrderStatusCountResponse(o.status, count(o))
            from Order o
            group by o.status
            order by o.status
            """)
    List<com.beautystor.dto.statistics.OrderStatusCountResponse> findOrderStatusCounts();
}
