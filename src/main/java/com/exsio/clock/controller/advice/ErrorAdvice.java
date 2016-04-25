package com.exsio.clock.controller.advice;


import com.exsio.clock.model.JsonpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorAdvice {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object onException(Exception ex, HttpServletRequest request) {
        LOGGER.error("MVC error: {}", ex.getMessage(), ex);
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return new ResponseEntity(JsonpResult.error(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return "/500.html";
        }
    }
}
