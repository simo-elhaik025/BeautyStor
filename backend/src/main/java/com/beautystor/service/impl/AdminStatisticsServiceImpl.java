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
import com.beautystor.service.AdminStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminStatisticsServiceImpl implements AdminStatisticsService {

    private static final int DEFAULT_PERIOD_DAYS = 30;
    private static final int TOP_PRODUCTS_LIMIT = 5;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminStatisticsResponse getStatistics(StatisticsPeriod period) {
        StatisticsWindow window = resolveWindow(period);

        List<DailyOrderStatResponse> rawOrders = window.monthlyGranularity()
                ? orderRepository.findMonthlyOrderStats(window.startDateTime(), window.endDateTime())
                : orderRepository.findDailyOrderStats(window.startDateTime(), window.endDateTime());
        List<DailyRevenueStatResponse> rawRevenue = window.monthlyGranularity()
                ? orderRepository.findMonthlyDeliveredRevenue(window.startDateTime(), window.endDateTime())
                : orderRepository.findDailyDeliveredRevenue(window.startDateTime(), window.endDateTime());
        List<OrderStatusCountResponse> rawOrderStatuses = orderRepository.findOrderStatusCounts();
        List<TopSoldProductResponse> topProducts = orderItemRepository.findTopSoldProducts(PageRequest.of(0, TOP_PRODUCTS_LIMIT));

        Map<LocalDate, DailyOrderStatResponse> ordersSeries = window.monthlyGranularity()
                ? initializeMonthlyOrders(window.periodStart(), window.periodEnd())
                : initializeDailyOrders(window.periodStart(), window.periodEnd());
        Map<LocalDate, BigDecimal> revenueSeries = window.monthlyGranularity()
                ? initializeMonthlyRevenue(window.periodStart(), window.periodEnd())
                : initializeDailyRevenue(window.periodStart(), window.periodEnd());
        Map<OrderStatus, Long> orderStatuses = initializeOrderStatuses();

        for (DailyOrderStatResponse row : rawOrders) {
            LocalDate date = toDate(row, window.monthlyGranularity());
            DailyOrderStatResponse current = ordersSeries.get(date);
            if (current != null) {
                current.setOrderCount(row.getOrderCount());
            }
        }

        for (DailyRevenueStatResponse row : rawRevenue) {
            LocalDate date = toDate(row, window.monthlyGranularity());
            revenueSeries.put(date, row.getRevenue() == null ? BigDecimal.ZERO : row.getRevenue());
        }

        for (OrderStatusCountResponse row : rawOrderStatuses) {
            orderStatuses.put(row.getStatus(), row.getCount());
        }

        long totalOrdersInPeriod = ordersSeries.values().stream()
                .mapToLong(DailyOrderStatResponse::getOrderCount)
                .sum();

        BigDecimal deliveredRevenueInPeriod = revenueSeries.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<DailyOrderStatResponse> ordersByDay = ordersSeries.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();

        return new AdminStatisticsResponse(
                window.periodStart(),
                window.periodEnd(),
                totalOrdersInPeriod,
                deliveredRevenueInPeriod,
                ordersByDay,
                orderStatuses.entrySet().stream()
                        .map(entry -> new OrderStatusCountResponse(entry.getKey(), entry.getValue()))
                        .toList(),
                topProducts);
    }

    private Map<LocalDate, DailyOrderStatResponse> initializeDailyOrders(LocalDate start, LocalDate end) {
        Map<LocalDate, DailyOrderStatResponse> result = new LinkedHashMap<>();
        LocalDate current = start;
        while (!current.isAfter(end)) {
            result.put(current, new DailyOrderStatResponse(current.getYear(), current.getMonthValue(), current.getDayOfMonth(), 0L));
            current = current.plusDays(1);
        }
        return result;
    }

    private Map<LocalDate, DailyOrderStatResponse> initializeMonthlyOrders(LocalDate start, LocalDate end) {
        Map<LocalDate, DailyOrderStatResponse> result = new LinkedHashMap<>();
        LocalDate current = start.withDayOfMonth(1);
        LocalDate last = end.withDayOfMonth(1);
        while (!current.isAfter(last)) {
            result.put(current, new DailyOrderStatResponse(current.getYear(), current.getMonthValue(), 1, 0L));
            current = current.plusMonths(1);
        }
        return result;
    }

    private Map<LocalDate, BigDecimal> initializeDailyRevenue(LocalDate start, LocalDate end) {
        Map<LocalDate, BigDecimal> result = new LinkedHashMap<>();
        LocalDate current = start;
        while (!current.isAfter(end)) {
            result.put(current, BigDecimal.ZERO);
            current = current.plusDays(1);
        }
        return result;
    }

    private Map<LocalDate, BigDecimal> initializeMonthlyRevenue(LocalDate start, LocalDate end) {
        Map<LocalDate, BigDecimal> result = new LinkedHashMap<>();
        LocalDate current = start.withDayOfMonth(1);
        LocalDate last = end.withDayOfMonth(1);
        while (!current.isAfter(last)) {
            result.put(current, BigDecimal.ZERO);
            current = current.plusMonths(1);
        }
        return result;
    }

    private Map<OrderStatus, Long> initializeOrderStatuses() {
        Map<OrderStatus, Long> result = new LinkedHashMap<>();
        for (OrderStatus status : OrderStatus.values()) {
            result.put(status, 0L);
        }
        return result;
    }

    private StatisticsWindow resolveWindow(StatisticsPeriod period) {
        StatisticsPeriod effectivePeriod = period == null ? StatisticsPeriod.MONTH : period;
        LocalDate periodEnd = LocalDate.now();

        return switch (effectivePeriod) {
            case WEEK -> new StatisticsWindow(
                    periodEnd.minusDays(6),
                    periodEnd,
                    periodEnd.minusDays(6).atStartOfDay(),
                    periodEnd.plusDays(1).atStartOfDay(),
                    false);
            case MONTH -> new StatisticsWindow(
                    periodEnd.minusDays(29),
                    periodEnd,
                    periodEnd.minusDays(29).atStartOfDay(),
                    periodEnd.plusDays(1).atStartOfDay(),
                    false);
            case YEAR -> {
                LocalDate start = periodEnd.minusMonths(11).withDayOfMonth(1);
                yield new StatisticsWindow(
                        start,
                        periodEnd,
                        start.atStartOfDay(),
                        periodEnd.plusDays(1).atStartOfDay(),
                        true);
            }
        };
    }

    private LocalDate toDate(DailyOrderStatResponse row, boolean monthly) {
        return monthly
                ? LocalDate.of(row.getYear(), row.getMonth(), 1)
                : LocalDate.of(row.getYear(), row.getMonth(), row.getDay());
    }

    private LocalDate toDate(DailyRevenueStatResponse row, boolean monthly) {
        return monthly
                ? LocalDate.of(row.getYear(), row.getMonth(), 1)
                : LocalDate.of(row.getYear(), row.getMonth(), row.getDay());
    }

    private record StatisticsWindow(
            LocalDate periodStart,
            LocalDate periodEnd,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            boolean monthlyGranularity) {
    }
}
