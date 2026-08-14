package com.beautystor.repository;

import com.beautystor.entity.OrderItem;
import com.beautystor.dto.statistics.TopSoldProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    void deleteByOrderId(long orderId);

    @Query("""
            select new com.beautystor.dto.statistics.TopSoldProductResponse(
                oi.variant.productId,
                max(oi.productNameSnapshot),
                sum(oi.quantity))
            from OrderItem oi
            join oi.order o
            where o.status = com.beautystor.enm.OrderStatus.DELIVERED
            group by oi.variant.productId
            order by sum(oi.quantity) desc, oi.variant.productId asc
            """)
    List<TopSoldProductResponse> findTopSoldProducts(Pageable pageable);
}
