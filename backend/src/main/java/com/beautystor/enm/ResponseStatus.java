package com.beautystor.enm;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Statut standard d'une réponse API.")
public enum ResponseStatus {
    SUCCESS,
    ERROR;

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }
}
