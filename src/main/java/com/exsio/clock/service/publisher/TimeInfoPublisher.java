package com.exsio.clock.service.publisher;

import com.exsio.clock.model.TimeInfo;

public interface TimeInfoPublisher {

    void publish(TimeInfo timeInfo);
}
