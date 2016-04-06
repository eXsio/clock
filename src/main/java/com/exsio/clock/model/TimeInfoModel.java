package com.exsio.clock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class TimeInfoModel implements Serializable {

    private final String time;

    private final boolean alert;

    private final boolean clockStarted;

    public TimeInfoModel(String time, boolean alert, boolean clockStarted) {
        this.time = time;
        this.alert = alert;
        this.clockStarted = clockStarted;
    }

    public String getTime() {
        return time;
    }

    public boolean isAlert() {
        return alert;
    }

    public boolean isClockStarted() {
        return clockStarted;
    }
}
