package com.exsio.clock.controller;

import com.exsio.clock.annotation.JsonpController;
import com.exsio.clock.model.JsonpResult;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.clock.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@JsonpController
@RequestMapping("/api")
public class ClockController {

    private final ClockService clockService;

    @Autowired
    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }

    @RequestMapping(value = "/set/{minutes}/{seconds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonpResult set(@PathVariable("minutes") int minutes, @PathVariable("seconds") int seconds) {
        clockService.set(minutes, seconds);
        return JsonpResult.success();
    }

    @RequestMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonpResult reset() {
        clockService.reset();
        return JsonpResult.success();
    }

    @RequestMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonpResult start() {
        clockService.start();
        return JsonpResult.success();
    }

    @RequestMapping(value = "/stop", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonpResult stop() {
        clockService.stop();
        return JsonpResult.success();
    }

    @RequestMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeInfo getClockState() {
        return clockService.getClockState();
    }
}
