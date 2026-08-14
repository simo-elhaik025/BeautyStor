package com.beautystor.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatisticsResponse {

    private LocalDate periodStart;
    private LocalDate periodEnd;
    private long totalOrdersInPeriod;
    private java.math.BigDecimal deliveredRevenueInPeriod;
    private List<DailyOrderStatResponse> ordersByDay;
    private List<OrderStatusCountResponse> orderStatuses;
    private List<TopSoldProductResponse> topProducts;
}
