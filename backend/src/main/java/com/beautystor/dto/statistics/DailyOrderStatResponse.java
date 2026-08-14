package com.beautystor.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyOrderStatResponse {

    private int year;
    private int month;
    private int day;
    private long orderCount;
}
