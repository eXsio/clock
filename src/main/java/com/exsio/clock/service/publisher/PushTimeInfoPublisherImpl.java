package com.exsio.clock.service.publisher;

import com.exsio.clock.model.TimeInfoModel;
import com.exsio.clock.model.PushMessage;
import com.exsio.clock.service.push.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushTimeInfoPublisherImpl implements TimeInfoPublisher {

    private final String CLOCK_CHANNEL = "clock";
    private final String CLOCK_MESSAGE_TYPE = "CLOCK";

    private final PushService pushService;

    @Autowired
    public PushTimeInfoPublisherImpl(PushService pushService) {
        this.pushService = pushService;
    }


    @Override
    public void publish(TimeInfoModel timeInfo) {
        pushService.push(CLOCK_CHANNEL, new PushMessage(CLOCK_MESSAGE_TYPE, timeInfo));
    }
}
