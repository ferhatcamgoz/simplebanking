package com.eteration.simplebanking.common.error;

import lombok.Data;

@Data
public class GenericError {

    private String message;
    private long timestamp;

    public GenericError(String message, long time) {
        this.message = message;
        this.timestamp = time;
    }
}
