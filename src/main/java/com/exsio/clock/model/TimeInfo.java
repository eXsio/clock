package com.exsio.clock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class TimeInfo implements Serializable {

    private final String time;

    private final String boundary;

    private final boolean alert;

    private final boolean clockStarted;

    public TimeInfo(String time, String boundary, boolean alert, boolean clockStarted) {
        this.time = time;
        this.boundary = boundary;
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

    public String getBoundary() {
        return boundary;
    }
}
