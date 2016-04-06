package com.exsio.clock.service.publisher;

import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.model.TimeInfoModel;
import com.exsio.clock.util.SpringProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.UI)
public class SpringTimeInfoPublisherImpl implements TimeInfoPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public SpringTimeInfoPublisherImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(TimeInfoModel timeInfo) {
        eventPublisher.publishEvent(new TimeChangedEvent(timeInfo));
    }
}
