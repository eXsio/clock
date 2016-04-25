package com.exsio.clock.exception;

public class TimeOverflowRuntimeException extends RuntimeException {

    public TimeOverflowRuntimeException(String requested, String limit) {
        super(String.format("You tried to initialize the time with value '%s', bu the acceptable value is below '%s'", requested, limit));
    }
}
