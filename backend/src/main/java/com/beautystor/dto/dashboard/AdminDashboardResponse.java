package com.beautystor.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Synthèse chiffrée du dashboard administrateur.")
public class AdminDashboardResponse {

    private long totalUsers;
    private long totalOrders;
    private long pendingOrders;
    private long deliveredOrders;
    private long cancelledOrders;
    private long totalProducts;
    private long totalProductVariants;
    private long outOfStockVariants;
    private long totalStockQuantity;
    private BigDecimal deliveredRevenue;
}
