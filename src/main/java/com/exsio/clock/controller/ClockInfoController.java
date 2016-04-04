package com.exsio.clock.controller;

import com.exsio.clock.model.ClockInfoModel;
import com.exsio.clock.model.PushMessage;
import com.exsio.clock.model.UpdateResult;
import com.exsio.clock.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClockInfoController {

    private final PushService pushService;

    @Autowired
    public ClockInfoController(PushService pushService) {
        this.pushService = pushService;
    }


    @RequestMapping("/hello")
    public String hello() {
        return "Greetings from the clock!";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public UpdateResult updateClockInfo(@RequestBody ClockInfoModel infoModel) {
        pushService.push("clock", new PushMessage("CLOCK", infoModel));
        return new UpdateResult(UpdateResult.Status.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UpdateResult exceptionHandler(Exception ex) {
        return new UpdateResult(UpdateResult.Status.ERROR, ex.getMessage());
    }

}
