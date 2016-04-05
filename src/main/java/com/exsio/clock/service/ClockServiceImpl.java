package com.exsio.clock.service;

import com.exsio.clock.model.Clock;
import com.exsio.clock.model.ClockInfoModel;
import com.exsio.clock.model.PushMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ClockServiceImpl implements ClockService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClockServiceImpl.class);

    private final PushService pushService;

    private final int SECOND = 1000;
    private final String CLOCK_CHANNEL = "clock";
    private final String CLOCK_MESSAGE_TYPE = "CLOCK";

    private int lastMinutes;
    private int lastSeconds;
    private Clock clock = new Clock(0, 0);
    private final Executor executor = Executors.newSingleThreadExecutor();
    private volatile boolean execute = false;

    @Autowired
    public ClockServiceImpl(PushService pushService) {
        this.pushService = pushService;
    }

    @Override
    public void set(int minutes, int seconds) {
        clock = new Clock(minutes, seconds);
        lastMinutes = minutes;
        lastSeconds = seconds;
        updateClockInfo(execute);
    }

    @Override
    public void reset() {
        clock = new Clock(lastMinutes, lastSeconds);
        updateClockInfo(execute);
    }

    @Override
    public void start() {
        execute = true;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (execute) {
                    try {
                        Thread.sleep(SECOND);
                        clock.tick();
                        updateClockInfo(execute);
                    } catch (InterruptedException e) {
                        LOGGER.error("{}", e.getMessage(), e);
                    }
                }
            }
        });
    }

    private void updateClockInfo(boolean started) {
        pushService.push(CLOCK_CHANNEL, new PushMessage(CLOCK_MESSAGE_TYPE, new ClockInfoModel(clock.toString(), clock.isAlert(), started)));
    }

    @Override
    public void stop() {
        execute = false;
    }


}
