package com.exsio.clock.event;

import com.exsio.clock.model.TimeInfo;
import org.springframework.context.ApplicationEvent;

public class TimeChangedEvent extends ApplicationEvent {

    public TimeChangedEvent(TimeInfo source) {
        super(source);
    }

    public TimeInfo getObject() {
        return (TimeInfo) this.getSource();
    }
}
