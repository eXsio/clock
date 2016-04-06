package com.exsio.clock.service;

import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.model.Clock;
import com.exsio.clock.model.ClockInfoModel;
import com.exsio.clock.model.PushMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ClockServiceImpl implements ClockService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClockServiceImpl.class);

    private final PushService pushService;
    private final ApplicationEventPublisher eventPublisher;

    private final int SECOND = 1000;
    private final String CLOCK_CHANNEL = "clock";
    private final String CLOCK_MESSAGE_TYPE = "CLOCK";

    private int lastMinutes;
    private int lastSeconds;
    private Clock clock = new Clock(0, 0);
    private final Executor executor = Executors.newSingleThreadExecutor();
    private volatile boolean execute = false;

    @Autowired
    public ClockServiceImpl(PushService pushService, ApplicationEventPublisher eventPublisher) {
        this.pushService = pushService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void set(int minutes, int seconds) {
        clock = new Clock(minutes, seconds);
        lastMinutes = minutes;
        lastSeconds = seconds;
        updateClockInfo();
    }

    @Override
    public void reset() {
        clock = new Clock(lastMinutes, lastSeconds);
        updateClockInfo();
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
                        updateClockInfo();
                    } catch (InterruptedException e) {
                        LOGGER.error("{}", e.getMessage(), e);
                    }
                }
            }
        });
    }

    private void updateClockInfo() {
        ClockInfoModel model = new ClockInfoModel(clock.toString(), clock.isAlert(), execute);
        pushService.push(CLOCK_CHANNEL, new PushMessage(CLOCK_MESSAGE_TYPE, model));
        eventPublisher.publishEvent(new TimeChangedEvent(model));
    }

    @Override
    public void stop() {
        execute = false;
    }


}
