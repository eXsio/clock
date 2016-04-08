package com.exsio.clock.service.clock;

import com.exsio.clock.model.Clock;
import com.exsio.clock.model.Time;
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

    private Clock clock = new Clock();
    private Time boundary;

    private volatile boolean started = false;

    @Autowired
    public ClockServiceImpl(Collection<TimeInfoPublisher> timeInfoPublishers) {
        this.timeInfoPublishers = timeInfoPublishers;
        boundary = clock.getBoundary();
    }

    @Override
    public void set(int minutes, int seconds) {
        boundary = new Time(minutes, seconds);
        clock.setBoundary(boundary);
        updateTimeInfo();
    }

    @Override
    public void reset() {
        clock.reset();
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
        TimeInfo model = getClockState();
        for (TimeInfoPublisher publisher : timeInfoPublishers) {
            publisher.publish(model);
        }
    }

    @Override
    public TimeInfo getClockState() {
        return new TimeInfo(clock.getTime(), boundary.toString(), clock.isAlert(), started);
    }
}