package com.exsio.clock.service.clock;


import com.exsio.clock.model.TimeInfo;

public interface ClockService {

    void set(int minutes, int seconds);

    void reset();

    void start();

    void stop();

    TimeInfo getClockState();
}
