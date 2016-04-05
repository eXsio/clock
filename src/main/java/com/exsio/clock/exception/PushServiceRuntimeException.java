package com.exsio.clock.exception;

public class PushServiceRuntimeException extends RuntimeException {

    public PushServiceRuntimeException(String msg, Throwable previous) {
        super(msg, previous);
    }

    public PushServiceRuntimeException(String msg) {
        super(msg);
    }
}
