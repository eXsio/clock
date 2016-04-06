package com.exsio.clock.service.publisher;

import com.exsio.clock.model.TimeInfoModel;

public interface TimeInfoPublisher {

    void publish(TimeInfoModel timeInfo);
}
