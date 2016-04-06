package com.exsio.clock.event;

import com.exsio.clock.model.TimeInfoModel;
import org.springframework.context.ApplicationEvent;

public class TimeChangedEvent extends ApplicationEvent {

    public TimeChangedEvent(TimeInfoModel source) {
        super(source);
    }

    public TimeInfoModel getObject() {
        return (TimeInfoModel) this.getSource();
    }
}
