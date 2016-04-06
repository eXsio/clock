package com.exsio.clock.service.clock;


public interface ClockService {

    void set(int minutes, int seconds);

    void reset();

    void start();

    void stop();
}
