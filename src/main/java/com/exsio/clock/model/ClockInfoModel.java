package com.exsio.clock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ClockInfoModel implements Serializable {

    private final String clock;

    private final boolean alert;

    private final boolean started;

    public ClockInfoModel(String clock, boolean alert, boolean started) {
        this.clock = clock;
        this.alert = alert;
        this.started = started;
    }

    public String getClock() {
        return clock;
    }

    public boolean isAlert() {
        return alert;
    }

    public boolean isStarted() {
        return started;
    }
}
