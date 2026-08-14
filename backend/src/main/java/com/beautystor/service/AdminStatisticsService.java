package com.beautystor.service;

import com.beautystor.dto.statistics.AdminStatisticsResponse;
import com.beautystor.enm.StatisticsPeriod;

public interface AdminStatisticsService {

    AdminStatisticsResponse getStatistics(StatisticsPeriod period);
}
