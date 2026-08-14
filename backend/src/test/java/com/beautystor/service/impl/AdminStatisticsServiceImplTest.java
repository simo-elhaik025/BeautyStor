package com.beautystor.service.impl;

import com.beautystor.dto.statistics.AdminStatisticsResponse;
import com.beautystor.dto.statistics.DailyOrderStatResponse;
import com.beautystor.dto.statistics.DailyRevenueStatResponse;
import com.beautystor.dto.statistics.OrderStatusCountResponse;
import com.beautystor.dto.statistics.TopSoldProductResponse;
import com.beautystor.enm.OrderStatus;
import com.beautystor.enm.StatisticsPeriod;
import com.beautystor.repository.OrderItemRepository;
import com.beautystor.repository.OrderRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminStatisticsServiceImplTest {

    private final StatisticsRepositoryState repositoryState = new StatisticsRepositoryState();
    private final AdminStatisticsServiceImpl adminStatisticsService = new AdminStatisticsServiceImpl(
            repositoryState.orderRepository(),
            repositoryState.orderItemRepository());

    @Test
    void weekUsesDailyBuckets() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(6);
        repositoryState.dailyOrderRows.set(List.of(
                new DailyOrderStatResponse(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 2L),
                new DailyOrderStatResponse(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 1L)));
        repositoryState.dailyRevenueRows.set(List.of(
                new DailyRevenueStatResponse(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), new BigDecimal("30.00")),
                new DailyRevenueStatResponse(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), new BigDecimal("12.00"))));
        repositoryState.statusRows.set(List.of(
                new OrderStatusCountResponse(OrderStatus.PENDING, 4L),
                new OrderStatusCountResponse(OrderStatus.DELIVERED, 7L)));
        repositoryState.topProductsRows.set(List.of(new TopSoldProductResponse(11L, "Shampoo", 9L)));

        AdminStatisticsResponse response = adminStatisticsService.getStatistics(StatisticsPeriod.WEEK);

        assertEquals(start, response.getPeriodStart());
        assertEquals(today, response.getPeriodEnd());
        assertEquals(7, response.getOrdersByDay().size());
        assertEquals(2L, response.getOrdersByDay().get(0).getOrderCount());
        assertEquals(1L, response.getOrdersByDay().get(6).getOrderCount());
        assertEquals(3L, response.getTotalOrdersInPeriod());
        assertEquals(0, new BigDecimal("42.00").compareTo(response.getDeliveredRevenueInPeriod()));
        assertEquals(3, response.getOrderStatuses().size());
        assertEquals(0L, response.getOrderStatuses().get(2).getCount());
    }

    @Test
    void monthUsesDailyBuckets() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(29);
        repositoryState.dailyOrderRows.set(List.of(
                new DailyOrderStatResponse(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), 5L),
                new DailyOrderStatResponse(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 7L)));
        repositoryState.dailyRevenueRows.set(List.of(
                new DailyRevenueStatResponse(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), new BigDecimal("80.00")),
                new DailyRevenueStatResponse(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), new BigDecimal("20.00"))));
        repositoryState.statusRows.set(List.of(
                new OrderStatusCountResponse(OrderStatus.CANCELLED, 2L)));
        repositoryState.topProductsRows.set(List.of(new TopSoldProductResponse(11L, "Shampoo", 9L)));

        AdminStatisticsResponse response = adminStatisticsService.getStatistics(StatisticsPeriod.MONTH);

        assertEquals(start, response.getPeriodStart());
        assertEquals(today, response.getPeriodEnd());
        assertEquals(30, response.getOrdersByDay().size());
        assertEquals(5L, response.getOrdersByDay().get(0).getOrderCount());
        assertEquals(7L, response.getOrdersByDay().get(29).getOrderCount());
        assertEquals(12L, response.getTotalOrdersInPeriod());
        assertEquals(0, new BigDecimal("100.00").compareTo(response.getDeliveredRevenueInPeriod()));
    }

    @Test
    void yearUsesMonthlyBuckets() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusMonths(11).withDayOfMonth(1);
        repositoryState.monthlyOrderRows.set(List.of(
                new DailyOrderStatResponse(start.getYear(), start.getMonthValue(), 1, 3L),
                new DailyOrderStatResponse(today.getYear(), today.getMonthValue(), 1, 4L)));
        repositoryState.monthlyRevenueRows.set(List.of(
                new DailyRevenueStatResponse(start.getYear(), start.getMonthValue(), 1, new BigDecimal("300.00")),
                new DailyRevenueStatResponse(today.getYear(), today.getMonthValue(), 1, new BigDecimal("120.00"))));
        repositoryState.statusRows.set(List.of(
                new OrderStatusCountResponse(OrderStatus.PENDING, 4L),
                new OrderStatusCountResponse(OrderStatus.DELIVERED, 7L),
                new OrderStatusCountResponse(OrderStatus.CANCELLED, 1L)));
        repositoryState.topProductsRows.set(List.of(
                new TopSoldProductResponse(11L, "Shampoo", 9L),
                new TopSoldProductResponse(12L, "Mask", 6L)));

        AdminStatisticsResponse response = adminStatisticsService.getStatistics(StatisticsPeriod.YEAR);

        assertEquals(start, response.getPeriodStart());
        assertEquals(today, response.getPeriodEnd());
        assertEquals(12, response.getOrdersByDay().size());
        assertEquals(3L, response.getOrdersByDay().get(0).getOrderCount());
        assertEquals(4L, response.getOrdersByDay().get(11).getOrderCount());
        assertEquals(7L, response.getTotalOrdersInPeriod());
        assertEquals(0, new BigDecimal("420.00").compareTo(response.getDeliveredRevenueInPeriod()));
    }

    static class StatisticsRepositoryState {
        final AtomicReference<List<DailyOrderStatResponse>> dailyOrderRows = new AtomicReference<>(List.of());
        final AtomicReference<List<DailyRevenueStatResponse>> dailyRevenueRows = new AtomicReference<>(List.of());
        final AtomicReference<List<DailyOrderStatResponse>> monthlyOrderRows = new AtomicReference<>(List.of());
        final AtomicReference<List<DailyRevenueStatResponse>> monthlyRevenueRows = new AtomicReference<>(List.of());
        final AtomicReference<List<OrderStatusCountResponse>> statusRows = new AtomicReference<>(List.of());
        final AtomicReference<List<TopSoldProductResponse>> topProductsRows = new AtomicReference<>(List.of());

        OrderRepository orderRepository() {
            return proxy(OrderRepository.class, (proxy, method, args) -> switch (method.getName()) {
                case "findDailyOrderStats" -> dailyOrderRows.get();
                case "findDailyDeliveredRevenue" -> dailyRevenueRows.get();
                case "findMonthlyOrderStats" -> monthlyOrderRows.get();
                case "findMonthlyDeliveredRevenue" -> monthlyRevenueRows.get();
                case "findOrderStatusCounts" -> statusRows.get();
                case "count", "countByStatus" -> 0L;
                case "sumTotalAmountByStatus" -> BigDecimal.ZERO;
                case "findById", "findByIdAndUserId", "findByUserIdOrderByCreatedAtDesc", "findAllByOrderByCreatedAtDesc" -> Optional.empty();
                default -> defaultReturn(method);
            });
        }

        OrderItemRepository orderItemRepository() {
            return proxy(OrderItemRepository.class, (proxy, method, args) -> switch (method.getName()) {
                case "findTopSoldProducts" -> topProductsRows.get();
                case "count" -> 0L;
                case "findAll" -> List.of();
                default -> defaultReturn(method);
            });
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
