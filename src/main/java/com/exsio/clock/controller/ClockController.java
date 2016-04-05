package com.exsio.clock.controller;

import com.exsio.clock.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClockController {

    private final ClockService clockService;

    @Autowired
    public ClockController(ClockService clockService) {
        this.clockService = clockService;
    }

    @RequestMapping("/set/{minutes}/{seconds}")
    public void set(@PathVariable("minutes") int minutes, @PathVariable("seconds") int seconds ) {
        clockService.set(minutes, seconds);
    }

    @RequestMapping("/reset")
    public void reset() {
        clockService.reset();
    }

    @RequestMapping("/start")
    public void start() {
        clockService.start();
    }

    @RequestMapping("/stop")
    public void stop() {
        clockService.stop();
    }
}
