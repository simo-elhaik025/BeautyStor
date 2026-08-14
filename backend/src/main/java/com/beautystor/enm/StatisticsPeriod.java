package com.beautystor.enm;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Période de statistiques administratives.")
public enum StatisticsPeriod {
    WEEK,
    MONTH,
    YEAR
}
