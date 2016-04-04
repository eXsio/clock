package com.exsio.clock.model;

import java.io.Serializable;

public class ClockInfoModel implements Serializable {

    private String clock;

    private boolean alert;

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
