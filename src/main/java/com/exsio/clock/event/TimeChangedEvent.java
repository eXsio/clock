package com.exsio.clock.event;

import com.exsio.clock.model.ClockInfoModel;
import org.springframework.context.ApplicationEvent;

public class TimeChangedEvent extends ApplicationEvent {

    public TimeChangedEvent(ClockInfoModel source) {
        super(source);
    }

    public ClockInfoModel getObject() {
        return (ClockInfoModel) this.getSource();
    }
}
