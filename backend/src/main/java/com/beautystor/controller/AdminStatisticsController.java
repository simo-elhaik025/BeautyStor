package com.beautystor.controller;

import com.beautystor.common.ApiResponse;
import com.beautystor.dto.statistics.AdminStatisticsResponse;
import com.beautystor.enm.StatisticsPeriod;
import com.beautystor.service.AdminStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/statistics")
@RequiredArgsConstructor
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;

    @GetMapping
    public ResponseEntity<ApiResponse<AdminStatisticsResponse>> getStatistics(
            @RequestParam(defaultValue = "MONTH") StatisticsPeriod period) {
        return ResponseEntity.ok(new ApiResponse<>(adminStatisticsService.getStatistics(period)));
    }
}
