package com.exsio.clock.exception;

/**
 * Created by A045494 on 10/7/2015.
 */
public class PushServiceRuntimeException extends RuntimeException {

    public PushServiceRuntimeException(String msg, Throwable previous) {
        super(msg, previous);
    }

    public PushServiceRuntimeException(String msg) {
        super(msg);
    }
}
