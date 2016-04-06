package com.exsio.clock.service.clock;

import com.exsio.clock.model.Clock;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.publisher.TimeInfoPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ClockServiceImpl implements ClockService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClockServiceImpl.class);
    private final static int SECOND = 1000;

    private final Collection<TimeInfoPublisher> timeInfoPublishers;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private Clock clock = new Clock(0, 0);
    private int lastMinutes = 0;
    private int lastSeconds = 0;
    private volatile boolean started = false;

    @Autowired
    public ClockServiceImpl(Collection<TimeInfoPublisher> timeInfoPublishers) {
        this.timeInfoPublishers = timeInfoPublishers;
    }

    @Override
    public void set(int minutes, int seconds) {
        clock = new Clock(minutes, seconds);
        lastMinutes = minutes;
        lastSeconds = seconds;
        updateTimeInfo();
    }

    @Override
    public void reset() {
        clock = new Clock(lastMinutes, lastSeconds);
        updateTimeInfo();
    }

    @Override
    public void start() {
        started = true;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (started) {
                    try {
                        Thread.sleep(SECOND);
                        clock.tick();
                        updateTimeInfo();
                    } catch (InterruptedException e) {
                        LOGGER.error("{}", e.getMessage(), e);
                    }
                }
            }
        });
    }

    @Override
    public void stop() {
        started = false;
    }

    private void updateTimeInfo() {
        TimeInfo model = new TimeInfo(clock.getTime(), clock.isAlert(), started);
        for (TimeInfoPublisher publisher : timeInfoPublishers) {
            publisher.publish(model);
        }
    }
}