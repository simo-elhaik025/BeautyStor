package com.beautystor.service.impl;

import com.beautystor.dto.dashboard.AdminDashboardResponse;
import com.beautystor.enm.OrderStatus;
import com.beautystor.repository.OrderRepository;
import com.beautystor.repository.ProductRepository;
import com.beautystor.repository.ProductVariantRepository;
import com.beautystor.repository.UserRepository;
import com.beautystor.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard() {
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);
        long deliveredOrders = orderRepository.countByStatus(OrderStatus.DELIVERED);
        long cancelledOrders = orderRepository.countByStatus(OrderStatus.CANCELLED);
        long totalProducts = productRepository.count();
        long totalProductVariants = productVariantRepository.count();
        long outOfStockVariants = productVariantRepository.countByStockQuantityLessThanEqual(0);
        long totalStockQuantity = safeLong(productVariantRepository.sumStockQuantity());
        BigDecimal deliveredRevenue = safeBigDecimal(orderRepository.sumTotalAmountByStatus(OrderStatus.DELIVERED));

        return new AdminDashboardResponse(
                totalUsers,
                totalOrders,
                pendingOrders,
                deliveredOrders,
                cancelledOrders,
                totalProducts,
                totalProductVariants,
                outOfStockVariants,
                totalStockQuantity,
                deliveredRevenue);
    }

    private long safeLong(Long value) {
        return value == null ? 0L : value;
    }

    private BigDecimal safeBigDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
