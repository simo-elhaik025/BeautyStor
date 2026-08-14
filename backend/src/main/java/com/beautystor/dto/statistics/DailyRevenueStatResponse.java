package com.beautystor.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRevenueStatResponse {

    private int year;
    private int month;
    private int day;
    private BigDecimal revenue;
}
