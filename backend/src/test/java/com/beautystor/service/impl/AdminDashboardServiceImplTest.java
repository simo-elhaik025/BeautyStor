package com.beautystor.service.impl;

import com.beautystor.enm.OrderStatus;
import com.beautystor.repository.OrderRepository;
import com.beautystor.repository.ProductRepository;
import com.beautystor.repository.ProductVariantRepository;
import com.beautystor.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminDashboardServiceImplTest {

    private final DashboardRepositoryState repositoryState = new DashboardRepositoryState();
    private final AdminDashboardServiceImpl adminDashboardService = new AdminDashboardServiceImpl(
            repositoryState.userRepository(),
            repositoryState.orderRepository(),
            repositoryState.productRepository(),
            repositoryState.productVariantRepository());

    @Test
    void getDashboardAggregatesCountsFromRepositories() {
        repositoryState.userCount.set(12L);
        repositoryState.orderCount.set(20L);
        repositoryState.orderCounts.put(OrderStatus.PENDING, 5L);
        repositoryState.orderCounts.put(OrderStatus.DELIVERED, 11L);
        repositoryState.orderCounts.put(OrderStatus.CANCELLED, 4L);
        repositoryState.productCount.set(8L);
        repositoryState.variantCount.set(15L);
        repositoryState.outOfStockCount.set(3L);
        repositoryState.stockQuantity.set(42L);
        repositoryState.deliveredRevenue = new BigDecimal("1234.50");

        var response = adminDashboardService.getDashboard();

        assertEquals(12L, response.getTotalUsers());
        assertEquals(20L, response.getTotalOrders());
        assertEquals(5L, response.getPendingOrders());
        assertEquals(11L, response.getDeliveredOrders());
        assertEquals(4L, response.getCancelledOrders());
        assertEquals(8L, response.getTotalProducts());
        assertEquals(15L, response.getTotalProductVariants());
        assertEquals(3L, response.getOutOfStockVariants());
        assertEquals(42L, response.getTotalStockQuantity());
        assertEquals(0, new BigDecimal("1234.50").compareTo(response.getDeliveredRevenue()));
    }

    static class DashboardRepositoryState {
        final AtomicLong userCount = new AtomicLong();
        final AtomicLong orderCount = new AtomicLong();
        final Map<OrderStatus, Long> orderCounts = new EnumMap<>(OrderStatus.class);
        final AtomicLong productCount = new AtomicLong();
        final AtomicLong variantCount = new AtomicLong();
        final AtomicLong outOfStockCount = new AtomicLong();
        final AtomicLong stockQuantity = new AtomicLong();
        BigDecimal deliveredRevenue = BigDecimal.ZERO;

        UserRepository userRepository() {
            return proxy(UserRepository.class, (proxy, method, args) -> handleUserRepository(method));
        }

        OrderRepository orderRepository() {
            return proxy(OrderRepository.class, (proxy, method, args) -> handleOrderRepository(method, args));
        }

        ProductRepository productRepository() {
            return proxy(ProductRepository.class, (proxy, method, args) -> handleProductRepository(method));
        }

        ProductVariantRepository productVariantRepository() {
            return proxy(ProductVariantRepository.class, (proxy, method, args) -> handleProductVariantRepository(method, args));
        }

        private Object handleUserRepository(Method method) {
            return switch (method.getName()) {
                case "count" -> userCount.get();
                default -> defaultReturn(method);
            };
        }

        private Object handleOrderRepository(Method method, Object[] args) {
            return switch (method.getName()) {
                case "count" -> orderCount.get();
                case "countByStatus" -> orderCounts.getOrDefault((OrderStatus) args[0], 0L);
                case "sumTotalAmountByStatus" -> deliveredRevenue;
                default -> defaultReturn(method);
            };
        }

        private Object handleProductRepository(Method method) {
            return switch (method.getName()) {
                case "count" -> productCount.get();
                default -> defaultReturn(method);
            };
        }

        private Object handleProductVariantRepository(Method method, Object[] args) {
            return switch (method.getName()) {
                case "count" -> variantCount.get();
                case "countByStockQuantityLessThanEqual" -> outOfStockCount.get();
                case "sumStockQuantity" -> stockQuantity.get();
                default -> defaultReturn(method);
            };
        }

        private Object defaultReturn(Method method) {
            Class<?> returnType = method.getReturnType();
            if (returnType.equals(boolean.class)) {
                return false;
            }
            if (returnType.equals(int.class)) {
                return 0;
            }
            if (returnType.equals(long.class)) {
                return 0L;
            }
            if (returnType.equals(double.class)) {
                return 0d;
            }
            if (returnType.equals(float.class)) {
                return 0f;
            }
            if (returnType.equals(short.class)) {
                return (short) 0;
            }
            if (returnType.equals(byte.class)) {
                return (byte) 0;
            }
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        private static <T> T proxy(Class<T> type, InvocationHandler handler) {
            return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler);
        }
    }
}
