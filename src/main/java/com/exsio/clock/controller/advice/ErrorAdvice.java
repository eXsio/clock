package com.exsio.clock.controller.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdvice {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorAdvice.class);

    @ExceptionHandler(Exception.class)
    public void onException(Exception ex) {
        LOGGER.error("{}", ex.getMessage(), ex);
    }
}
