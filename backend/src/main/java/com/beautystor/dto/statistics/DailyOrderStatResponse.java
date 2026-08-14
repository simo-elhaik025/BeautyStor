package com.beautystor.dto.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Point de série temporelle pour le nombre de commandes.")
public class DailyOrderStatResponse {

    private int year;
    private int month;
    private int day;
    private long orderCount;
}
