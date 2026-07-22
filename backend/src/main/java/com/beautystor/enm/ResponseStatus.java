package com.beautystor.enm;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseStatus {
    SUCCESS,
    ERROR;

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }
}
