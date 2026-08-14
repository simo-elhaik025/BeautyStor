package com.beautystor.dto.statistics;

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
@Schema(description = "Point de série temporelle pour le chiffre d'affaires.")
public class DailyRevenueStatResponse {

    private int year;
    private int month;
    private int day;
    private BigDecimal revenue;
}
