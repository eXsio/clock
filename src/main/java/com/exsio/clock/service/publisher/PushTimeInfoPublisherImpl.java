package com.exsio.clock.service.publisher;

import com.exsio.clock.model.PushMessage;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.push.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushTimeInfoPublisherImpl implements TimeInfoPublisher {

    public final static String CLOCK_CHANNEL = "clock";

    private final PushService pushService;

    @Autowired
    public PushTimeInfoPublisherImpl(PushService pushService) {
        this.pushService = pushService;
    }

    @Override
    public void publish(TimeInfo timeInfo) {
        pushService.push(CLOCK_CHANNEL, new PushMessage(timeInfo));
    }
}
